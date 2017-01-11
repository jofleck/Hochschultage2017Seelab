package de.seelab.speechlight;

import de.seelab.speechlight.interfaces.HypothesisReceiver;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

/**
 * Created by Jonas Fleck on 06.12.16.
 */
public class SpeechRecognizer {

    private HypothesisReceiver receiver;

    public void setReceiver(HypothesisReceiver receiver) {
        this.receiver = receiver;
    }

    public void start() throws Exception {
        Configuration configuration = new Configuration();

        configuration
                .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration
                .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration
                .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
        configuration.setUseGrammar(true);
        configuration.setGrammarName("example");
        configuration.setGrammarPath("resource:/");
        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);



        recognizer.startRecognition(true);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            String hypothethis = result.getHypothesis();
            if(hypothethis.equals("<unk>")) {
                continue;
            }

            System.out.format("Hypothesis: %s\n", hypothethis);
            if(receiver != null) {
                receiver.handleHypothesis(hypothethis);
            }

        }
        recognizer.stopRecognition();
    }
    }

