package de.seelab.speechlight;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import de.seelab.speechlight.interfaces.HypothesisReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonas Fleck on 07.12.16.
 */

public class LightController implements HypothesisReceiver{
    private HueHandler hueHandler; //This is the connection to the hue system.

    private final int lightId = 1; //Your lightID here
    private final String ip = "172.22.57.234";
    private final String username = "PfVrekicZHdU6MK4tP96pLh0abNmAqRhkbRyn2Au";


    //This is a constructor. Everything you need to work gets initialized here.
    public LightController(HueHandler hueHandler) {
        this.hueHandler = hueHandler;
        hueHandler.connect(ip, username, lightId);
    }

    /*
     * The speech recognizer calls this method if it detects a sentence which matches
     * the given grammar.
     */
    public void handleHypothesis(String hypothesis) {
        //TODO : Your code :-)
    	

    }
}
