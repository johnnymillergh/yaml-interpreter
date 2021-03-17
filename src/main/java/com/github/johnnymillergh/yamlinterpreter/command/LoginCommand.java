package com.github.johnnymillergh.yamlinterpreter.command;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.Callable;

/**
 * Description: LoginCommand, change description here.
 *
 * @author 钟俊（zhongjun）, email: zhongjun@toguide.cn, date: 3/17/2021 2:32 PM
 **/
@Component
@CommandLine.Command(name = "login", mixinStandardHelpOptions = true, version = "login 1.0", description = "User " +
        "login.")
public class LoginCommand implements Callable<Integer> {
    @CommandLine.Option(names = {"-u", "--user"}, description = "User name")
    String user;

    @CommandLine.Option(names = {"-p", "--password"}, description = "Passphrase")
    String password;

    @Override
    public Integer call() throws Exception {
        byte[] bytes = password.getBytes();

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bytes);

        System.out.printf("Hi %s, your password (%s) is hashed to %s.%n", user, password, base64(md.digest()));

        // null out the arrays when done
        Arrays.fill(bytes, (byte) 0);

        return 0;
    }

    private String base64(byte[] arr) {
        Base64.Encoder base64Encoder = Base64.getEncoder();
        return base64Encoder.encodeToString(arr);
    }
}
