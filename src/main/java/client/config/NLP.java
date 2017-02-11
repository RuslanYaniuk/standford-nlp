package client.config;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class NLP {

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public StanfordCoreNLP standfordCorePipeline() throws IOException {
        InputStream input = resourceLoader
                .getResource("classpath:StanfordCoreNLP-german.properties")
                .getInputStream();
        Properties prop = new Properties();

        prop.load(input);
        return new StanfordCoreNLP(prop);
    }
}
