package com.github.johnnymillergh.yamlinterpreter.command;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import picocli.CommandLine;

import java.io.*;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Description: YamlInterpreterCommand, change description here.
 *
 * @author Johnny Miller (锺俊), email: johnnysviva@outlook.com, date: 3/17/2021 5:14 PM
 **/
@Data
@Slf4j
@Component
@CommandLine.Command(
        name = "yaml-interpreter",
        aliases = "ymlitp",
        mixinStandardHelpOptions = true,
        version = "ymlitp 1.0",
        description = "YAML Interpreter command."
)
public class YamlInterpreterCommand implements Callable<Integer> {
    @CommandLine.Option(
            names = {"-dp", "--docker-compose"},
            description = "Docker compose file",
            required = true
    )
    private File dockerCompose;

    @CommandLine.Option(
            names = {"-env", "--dot-env"},
            description = "Env file",
            required = true
    )
    private File dotEnv;

    @Override
    public Integer call() throws FileNotFoundException {
        log.info("Command options: {}", this.toString());
        if (!dockerCompose.exists()) {
            throw new IllegalArgumentException("`docker-compose.yml` not found! Check the path of Docker compose file");
        }
        if (!dotEnv.exists()) {
            throw new IllegalArgumentException("`.env` not found! Check the path of dot env file");
        }
        InputStream inputStream = new BufferedInputStream(new FileInputStream(dockerCompose));
        Yaml yaml = new Yaml();
        Map<String, Object> dockerComposeData = yaml.load(inputStream);
        log.info(dockerComposeData.toString());
        PrintWriter writer = new PrintWriter(String.format("./docker-compose%s.yml", Instant.now()));
        return 0;
    }
}
