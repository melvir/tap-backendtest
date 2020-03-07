package csit.tap.employee.external;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillsService {

    private SkillsHttpClient skillsHttpClient;
    private SkillRepository skillRepository;

    public SkillsService(SkillsHttpClient skillsHttpClient, SkillRepository skillRepository) {
        this.skillsHttpClient = skillsHttpClient;
        this.skillRepository = skillRepository;
    }

    public boolean getSkillsFromExternalSource() {
        List<Skill> skillsFromHttpClient = skillsHttpClient.getAllSkillsFromExternalApi();
        skillRepository.saveAll(skillsFromHttpClient);
        return true;
    }
}
