package de.seelab.speechlight;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.*;

import java.util.List;

/**
 * Created by Jonas Fleck on 07.12.16.
 */
public class HueHandler {
    PHHueSDK hueSDK;
    PHBridge bridge;
    PHLight selectedLight;
    private int lightId = 1;


    public HueHandler() {
        hueSDK = PHHueSDK.getInstance();
        hueSDK.getNotificationManager().registerSDKListener(listener);
    }


    private PHSDKListener listener = new PHSDKListener() {

        public void onAccessPointsFound(List<PHAccessPoint> accessPointsList) {

        }

        public void onAuthenticationRequired(PHAccessPoint accessPoint) {
            System.out.println("Please press the button on the bridge.");
            hueSDK.startPushlinkAuthentication(accessPoint);
        }

        public void onBridgeConnected(PHBridge bridge, String username) {
            HueHandler.this.bridge = bridge;
            System.out.println("Bridge connected. Username: " + username);
            PHBridgeResourcesCache cache = bridge.getResourceCache();
            List<PHLight> allLights = cache.getAllLights();
            for(PHLight light : allLights) {
                if(light.getIdentifier().equals("" + lightId)) {
                    selectedLight = light;
                    break;
                }
            }
        }

        public void onCacheUpdated(List<Integer> arg0, PHBridge arg1) {
        }

        public void onConnectionLost(PHAccessPoint arg0) {
            System.out.println("Connection lost");
        }

        public void onConnectionResumed(PHBridge arg0) {
        }

        public void onError(int code, final String message) {
            if (code == PHMessageType.PUSHLINK_BUTTON_NOT_PRESSED) {
                System.out.println("Please press the button on the bridge to generate a username");
            }
            else {
                System.out.println("Error: " + message);
            }

        }


        public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {
            for (PHHueParsingError parsingError: parsingErrorsList) {
                System.out.println("ParsingError : " + parsingError.getMessage());
            }
        }
    };

    public void connect(String ip, String username, int lightId) {
        this.lightId = lightId;
        PHAccessPoint accessPoint = new PHAccessPoint();
        accessPoint.setIpAddress(ip);
        if(username != null)
            accessPoint.setUsername(username);
        hueSDK.connect(accessPoint);
    }



    public void updateWhite() {
        updateRGB(255, 255, 255, 100);
    }

    public void updateOff() {
        updateRGB(0, 0, 0, 0);
    }


    public void updateRGB(int r, int g, int b, int brightness) {
        assert  r > 0 && r <= 255 && g > 0 && g <= 255 && b > 0 && b <= 255
                && brightness > 0 && brightness <= 100;
        if(selectedLight == null) {
            return;
        }
        float xy[] = PHUtilities.calculateXYFromRGB(r, g, b, selectedLight.getModelNumber());
        PHLightState lightState = new PHLightState();
        if(brightness > 0) {
            lightState.setOn(true);
            lightState.setX(xy[0]);
            lightState.setY(xy[1]);
            lightState.setBrightness(brightness);
        } else {
            lightState.setOn(false);
        }

        bridge.updateLightState(selectedLight, lightState);
    }
}
