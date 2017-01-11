package de.seelab.speechlight;

/**
 * Created by Jonas Fleck on 07.12.16.
 */
public class Main {
    public static void main(String[] args) throws Exception{
        SpeechRecognizer recongizer  = new SpeechRecognizer();
        HueHandler hueHandler = new HueHandler();
        LightController controller = new LightController(hueHandler);

        recongizer.setReceiver(controller);
        recongizer.start();
    }

}
