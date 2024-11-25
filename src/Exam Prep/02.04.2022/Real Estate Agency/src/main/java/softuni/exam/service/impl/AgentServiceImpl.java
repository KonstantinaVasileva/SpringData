package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentSeedDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.repository.AgentRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AgentServiceImpl implements AgentService {

    private final String FILE_PATH = "src/main/resources/files/json/agents.json";

    private final AgentRepository agentRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public AgentServiceImpl(AgentRepository agentRepository, ModelMapper mapper, ValidationUtil validationUtil, Gson gson) {
        this.agentRepository = agentRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder output = new StringBuilder();
        AgentSeedDTO[] agentSeedDTOS = gson.fromJson(new FileReader(FILE_PATH), AgentSeedDTO[].class);
        for (AgentSeedDTO agentSeedDTO : agentSeedDTOS) {
            if (!validationUtil.isValid(agentSeedDTO) ||
                    agentRepository.findByFirstName(agentSeedDTO.getFirstName()).isPresent()) {
                output.append("Invalid agent").append(System.lineSeparator());
                continue;
            }
            Agent agent = mapper.map(agentSeedDTO, Agent.class);
            agentRepository.saveAndFlush(agent);
            output.append(String.format("Successfully imported agent - %s %s\n",
                    agent.getFirstName(),
                    agent.getLastName()));
        }
        return output.toString();
    }
}
