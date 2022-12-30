package com.example.stonks.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.example.stonks.dto.TargetDto;
import com.example.stonks.dto.UserDto;
import com.example.stonks.model.Target;
import com.example.stonks.model.User;
import com.example.stonks.repository.TargetRepository;
import com.example.stonks.repository.UserRepository;

@Service
public class TargetServiceImpl implements TargetService {

    private TargetRepository targetRepository;
    private UserRepository userRepository;

    public TargetServiceImpl(TargetRepository targetRepository, UserRepository userRepository) {
        this.targetRepository = targetRepository;
        this.userRepository = userRepository;
    }    

    @Override
    public void saveTarget(TargetDto targetDto, UserDto userDto) {
        Target target = new Target();
        target.setName(targetDto.getName());
        target.setCurrent_sum(targetDto.getCurrent_sum());
        target.setFinal_sum(targetDto.getFinal_sum());

        User user = userRepository.findByEmail(userDto.getEmail());
        target.setUser(user);

        targetRepository.save(target);
    }

    @Override
    public void updateTarget(TargetDto targetDto) {
        Target target = targetRepository.findByIdAndName(targetDto.getId(), targetDto.getName());
        target.setCurrent_sum(targetDto.getCurrent_sum());
        targetRepository.save(target);
    }

    @Override
    public List<TargetDto> findTargetsForUser(UserDto user) {
        List<Target> targets = targetRepository.findByUserId(user.getId());
        return targets.stream()
                      .map((target) -> mapToTargetDto(target))
                      .collect(Collectors.toList());
    }

    @Override
    public TargetDto findTargetForUserByName(UserDto user, String name) {
        System.out.println(name);
        Target target = targetRepository.findByUserIdAndName(user.getId(), name);
        return mapToTargetDto(target);
    }

    private TargetDto mapToTargetDto(Target target) {
        TargetDto targetDto = new TargetDto();
        targetDto.setName(target.getName());
        Long current_sum = target.getCurrent_sum();
        Long final_sum = target.getFinal_sum();
        targetDto.setId(target.getId());
        targetDto.setCurrent_sum(current_sum);
        targetDto.setFinal_sum(final_sum);
        targetDto.setProgress(Math.round(current_sum / (double)final_sum * 100));
        return targetDto;
    }
}
