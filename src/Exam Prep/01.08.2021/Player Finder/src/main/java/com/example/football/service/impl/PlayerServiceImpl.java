package com.example.football.service.impl;

import com.example.football.models.dto.xml.PlayerSeedDTO;
import com.example.football.models.dto.xml.PlayerSeedRootDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final String FILE_PATH = "src/main/resources/files/xml/players.xml";

    private final PlayerRepository playerRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final StatRepository statRepository;
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper mapper, ValidationUtil validationUtil, XmlParser xmlParser, StatRepository statRepository, TeamRepository teamRepository, TownRepository townRepository) {
        this.playerRepository = playerRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.statRepository = statRepository;
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException {
        StringBuilder output = new StringBuilder();
        PlayerSeedRootDTO playerSeedRootDTO = xmlParser.fromFile(FILE_PATH, PlayerSeedRootDTO.class);
        for (PlayerSeedDTO playerSeedDTO : playerSeedRootDTO.getPlayerSeedDTOS()) {
            if (!validationUtil.isValid(playerSeedDTO) ||
                    playerRepository.findByEmail(playerSeedDTO.getEmail()).isPresent()) {
                output.append("Invalid Player").append(System.lineSeparator());
                continue;
            }
            Player player = mapper.map(playerSeedDTO, Player.class);

            Stat stat = statRepository.findById(playerSeedDTO.getStatDTO().getId()).get();
            player.setStat(stat);
            Team team = teamRepository.findByName(playerSeedDTO.getTeamDTO().getName()).get();
            player.setTeam(team);
            Town town = townRepository.findByName(playerSeedDTO.getTownDTO().getName());
            player.setTown(town);
            playerRepository.saveAndFlush(player);
            output.append(String.format("Successfully imported Player %s %s - %s",
                    player.getFirstName(),
                    player.getLastName(),
                    player.getPosition()));
        }
        return output.toString();
    }

    @Override
    public String exportBestPlayers() {
        return playerRepository.findAllByBirthDateBetween(
                LocalDate.of(1995, 1, 1),
                LocalDate.of(2003, 1, 1))
                .stream().map(p->String.format("""
                                Player - %s %s
                                \tPosition - %s
                                \t\tTeam - %s
                                \tStadium - %s
                                """,
                        p.getFirstName(),
                        p.getLastName(),
                        p.getPosition(),
                        p.getTeam().getName(),
                        p.getTeam().getStadiumName()))
                .collect(Collectors.joining());
    }
}
