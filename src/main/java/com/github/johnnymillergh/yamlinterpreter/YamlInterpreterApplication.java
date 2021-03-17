package com.github.johnnymillergh.yamlinterpreter;

import com.github.johnnymillergh.yamlinterpreter.command.LoginCommand;
import com.github.johnnymillergh.yamlinterpreter.command.YamlInterpreterCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

/**
 * Description: YamlInterpreterApplication, change description here.
 *
 * @author Johnny Miller (锺俊), email: johnnysviva@outlook.com, date: 3/17/2021 2:43 PM
 **/
@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class YamlInterpreterApplication implements CommandLineRunner {
    private final LoginCommand loginCommand;
    private final YamlInterpreterCommand yamlInterpreterCommand;

    public static void main(String[] args) {
        SpringApplication.run(YamlInterpreterApplication.class, args);
    }

    @Override
    public void run(String... args) {
        CommandLine commandLine = new CommandLine(yamlInterpreterCommand);
        try {
            commandLine.execute(args);
            val executionResult = commandLine.getExecutionResult();
            log.info("typeof executionResult: {}, value: {}", executionResult.getClass().getSimpleName(),
                     executionResult);
        } catch (Exception e) {
            log.error("Exception occurred when executing command. Message: {}", e.getMessage());
        }
    }
}
