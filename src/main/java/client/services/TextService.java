package client.services;

import com.jayway.jsonpath.JsonPath;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TextService {

    @Autowired
    private StanfordCoreNLP pipeline;

    public List<String> parseCommentsWithNames(String json) throws IOException, ClassNotFoundException {
        List<String> res = new ArrayList<>();
        Annotation document;
        List<CoreMap> sentences;

        for (Object c : (JSONArray) JsonPath.read(json, "$.response.data.shop.reviews[*].comment")) {
            String comment = (String) c;
            document = new Annotation(comment);
            pipeline.annotate(document);
            sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
            for (CoreMap sentence : sentences) {
                boolean nameIsFound = false;
                for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                    String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                    if (ne.equals("I-PER")) {
                        res.add(comment);
                        nameIsFound = true;
                        break;
                    }
                }
                if (nameIsFound) {
                    break;
                }
            }
        }
        return res;
    }
}
