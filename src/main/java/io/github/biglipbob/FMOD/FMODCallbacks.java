package io.github.biglipbob.FMOD;

import com.sun.jna.Pointer;

import static io.github.biglipbob.FMOD.FMODCallbacks.FMODChannelCallback;
import static io.github.biglipbob.FMOD.FMODCallbacks.FMODChannelGroupCallback;
import static io.github.biglipbob.FMOD.FMODConstants.*;
import static io.github.biglipbob.FMOD.FMODFFI.FMODChannelCallbackFFI;
import static io.github.biglipbob.FMOD.FMODFFI.FMODChannelGroupCallbackFFI;

public interface FMODCallbacks {
    class FMODChannelCallback {
        public int onSoundEnd(FMODChannel channel) { return FMOD_OK; }
        public int onVirtualVoice(FMODChannel channel, boolean isVirtual) { return FMOD_OK; }
        public int onSyncPoint(FMODChannel channel, int index) { return FMOD_OK; }
        public int onOcclusion(FMODChannel channel, float direct, float reverb) { return FMOD_OK; }
    }

    class FMODChannelGroupCallback {
        public int onOcclusion(FMODChannelGroupRef channel, float direct, float reverb) { return FMOD_OK; }
    }
}

class FMODChannelCallbackAdapter implements FMODChannelCallbackFFI {
    FMODChannelCallback dispatcher;

    FMODChannelCallbackAdapter(FMODChannelCallback dispatcher) {
        this.dispatcher = dispatcher;
    }

    public int invoke(Pointer channelControl, int controlType, int callbackType, Pointer commandData1, Pointer commandData2) {
        if (controlType != FMOD_CHANNELCONTROL_CHANNEL)
            throw new IllegalStateException("Invalid ChannelControl type: " + controlType);
        return switch (callbackType) {
            case FMOD_CHANNELCONTROL_CALLBACK_END -> dispatcher.onSoundEnd(new FMODChannel(channelControl));
            case FMOD_CHANNELCONTROL_CALLBACK_VIRTUALVOICE ->
                    dispatcher.onVirtualVoice(new FMODChannel(channelControl), Pointer.nativeValue(commandData1) != 0);
            case FMOD_CHANNELCONTROL_CALLBACK_SYNCPOINT ->
                    dispatcher.onSyncPoint(new FMODChannel(channelControl), (int) Pointer.nativeValue(commandData1));
            case FMOD_CHANNELCONTROL_CALLBACK_OCCLUSION ->
                    dispatcher.onOcclusion(new FMODChannel(channelControl), commandData1.getFloat(0), commandData2.getFloat(0));
            default -> throw new IllegalStateException("Unknown ChannelC callback type: " + callbackType);
        };
    }
}

class FMODChannelGroupCallbackAdapter implements FMODChannelGroupCallbackFFI {
    FMODChannelGroupCallback dispatcher;

    FMODChannelGroupCallbackAdapter(FMODChannelGroupCallback dispatcher) {
        this.dispatcher = dispatcher;
    }

    public int invoke(Pointer channelControl, int controlType, int callbackType, Pointer commandData1, Pointer commandData2) {
        if (controlType != FMOD_CHANNELCONTROL_CHANNELGROUP)
            throw new IllegalStateException("Invalid ChannelControl type: " + controlType);
        return switch (callbackType) {
            case FMOD_CHANNELCONTROL_CALLBACK_OCCLUSION ->
                    dispatcher.onOcclusion(new FMODChannelGroupRef(channelControl), commandData1.getFloat(0), commandData2.getFloat(0));
            case FMOD_CHANNELCONTROL_CALLBACK_END, FMOD_CHANNELCONTROL_CALLBACK_VIRTUALVOICE,
                 FMOD_CHANNELCONTROL_CALLBACK_SYNCPOINT ->
                    throw new IllegalArgumentException("Channel Callback triggered for ChannelGroup instance");
            default -> throw new IllegalStateException("Unknown ChannelGroup callback type: " + callbackType);
        };
    }
}