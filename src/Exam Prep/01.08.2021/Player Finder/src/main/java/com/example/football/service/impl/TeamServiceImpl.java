package com.example.football.service.impl;

import com.example.football.models.dto.json.TeamSeedDTO;
import com.example.football.models.entity.Team;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TeamServiceImpl implements TeamService {

    private final String FILE_PATH = "src/main/resources/files/json/teams.json";

    private final TeamRepository teamRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final TownRepository townRepository;

    public TeamServiceImpl(TeamRepository teamRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importTeams() throws FileNotFoundException {
        StringBuilder output = new StringBuilder();
        TeamSeedDTO[] teamSeedDTOS = gson.fromJson(new FileReader(FILE_PATH), TeamSeedDTO[].class);
        for (TeamSeedDTO teamSeedDTO : teamSeedDTOS) {
            if (!validationUtil.isValid(teamSeedDTO)||
            teamRepository.findByName(teamSeedDTO.getName()).isPresent()) {
                output.append("Invalid team").append(System.lineSeparator());
                continue;
            }
            Team team = mapper.map(teamSeedDTO, Team.class);
            team.setTown(townRepository.findByName(teamSeedDTO.getTownName()));
            teamRepository.saveAndFlush(team);
            output.append(String.format("Successfully imported Team %s - %s\n",
                    team.getName(),
                    team.getFanBase()));
        }
        return output.toString();
    }
}
