package io.github.biglipbob.FMOD;

import static io.github.biglipbob.FMOD.FMODConstants.*;

public class FMODException extends RuntimeException {
    private static final String[] ERR_MSG = {
            "OK", "BADCOMMAND", "CHANNEL_ALLOC", "CHANNEL_STOLEN", "DMA", "DSP_CONNECTION", "DSP_DONTPROCESS", "DSP_FORMAT",
            "DSP_INUSE", "DSP_NOTFOUND", "DSP_RESERVED", "DSP_SILENCE", "DSP_TYPE", "FILE_BAD", "FILE_COULDNOTSEEK",
            "FILE_DISKEJECTED", "FILE_EOF", "FILE_ENDOFDATA", "FILE_NOTFOUND", "FORMAT", "HEADER_MISMATCH", "HTTP",
            "HTTP_ACCESS", "HTTP_PROXY_AUTH", "HTTP_SERVER_ERROR", "HTTP_TIMEOUT", "INITIALIZATION", "INITIALIZED",
            "INTERNAL", "INVALID_FLOAT", "INVALID_HANDLE", "INVALID_PARAM", "INVALID_POSITION", "INVALID_SPEAKER",
            "INVALID_SYNCPOINT", "INVALID_THREAD", "INVALID_VECTOR", "MAXAUDIBLE", "MEMORY", "MEMORY_CANTPOINT",
            "NEEDS3D", "NEEDSHARDWARE", "NET_CONNECT", "NET_SOCKET_ERROR", "NET_URL", "NET_WOULD_BLOCK", "NOTREADY",
            "OUTPUT_ALLOCATED", "OUTPUT_CREATEBUFFER", "OUTPUT_DRIVERCALL", "OUTPUT_FORMAT", "OUTPUT_INIT",
            "OUTPUT_NODRIVERS", "PLUGIN", "PLUGIN_MISSING", "PLUGIN_RESOURCE", "PLUGIN_VERSION", "RECORD",
            "REVERB_CHANNELGROUP", "REVERB_INSTANCE", "SUBSOUNDS", "SUBSOUND_ALLOCATED", "SUBSOUND_CANTMOVE",
            "TAGNOTFOUND", "TOOMANYCHANNELS", "TRUNCATED", "UNIMPLEMENTED", "UNINITIALIZED", "UNSUPPORTED", "VERSION",
            "EVENT_ALREADY_LOADED", "EVENT_LIVEUPDATE_BUSY", "EVENT_LIVEUPDATE_MISMATCH", "EVENT_LIVEUPDATE_TIMEOUT",
            "EVENT_NOTFOUND", "STUDIO_UNINITIALIZED", "STUDIO_NOT_LOADED", "INVALID_STRING", "ALREADY_LOCKED",
            "NOT_LOCKED", "RECORD_DISCONNECTED", "TOOMANYSAMPLES"
    };

    private final int code;

    public FMODException(String name, int code) {
        super(String.format("%s: %s (%d): %s", name, (0 <= code && code < 82 ? ERR_MSG[code] : "UNKNOWN"), code, description(code)));
        this.code = code;
    }

    private static String description(int code) {
        return switch (code) {
            case FMOD_OK -> "No error.";
            case FMOD_ERR_BADCOMMAND ->
                    "Tried to call a function on a data type that does not allow this type of functionality (ie calling Sound::lock on a streaming sound).";
            case FMOD_ERR_CHANNEL_ALLOC -> "Error trying to allocate a channel.";
            case FMOD_ERR_CHANNEL_STOLEN -> "The specified channel has been reused to play another sound.";
            case FMOD_ERR_DMA -> "DMA Failure.  See debug output for more information.";
            case FMOD_ERR_DSP_CONNECTION ->
                    "DSP connection error.  Connection possibly caused a cyclic dependency or connected dsps with incompatible buffer counts.";
            case FMOD_ERR_DSP_DONTPROCESS ->
                    "DSP return code from a DSP process query callback.  Tells mixer not to call the process callback and therefore not consume CPU.  Use this to optimize the DSP graph.";
            case FMOD_ERR_DSP_FORMAT ->
                    "DSP Format error.  A DSP unit may have attempted to connect to this network with the wrong format, or a matrix may have been set with the wrong size if the target unit has a specified channel map.";
            case FMOD_ERR_DSP_INUSE ->
                    "DSP is already in the mixer's DSP network. It must be removed before being reinserted or released.";
            case FMOD_ERR_DSP_NOTFOUND -> "DSP connection error.  Couldn't find the DSP unit specified.";
            case FMOD_ERR_DSP_RESERVED ->
                    "DSP operation error.  Cannot perform operation on this DSP as it is reserved by the system.";
            case FMOD_ERR_DSP_SILENCE ->
                    "DSP return code from a DSP process query callback.  Tells mixer silence would be produced from read, so go idle and not consume CPU.  Use this to optimize the DSP graph.";
            case FMOD_ERR_DSP_TYPE -> "DSP operation cannot be performed on a DSP of this type.";
            case FMOD_ERR_FILE_BAD -> "Error loading file.";
            case FMOD_ERR_FILE_COULDNOTSEEK ->
                    "Couldn't perform seek operation.  This is a limitation of the medium (ie netstreams) or the file format.";
            case FMOD_ERR_FILE_DISKEJECTED -> "Media was ejected while reading.";
            case FMOD_ERR_FILE_EOF ->
                    "End of file unexpectedly reached while trying to read essential data (truncated?).";
            case FMOD_ERR_FILE_ENDOFDATA -> "End of current chunk reached while trying to read data.";
            case FMOD_ERR_FILE_NOTFOUND -> "File not found.";
            case FMOD_ERR_FORMAT -> "Unsupported file or audio format.";
            case FMOD_ERR_HEADER_MISMATCH ->
                    "There is a version mismatch between the FMOD header and either the FMOD Studio library or the FMOD Low Level library.";
            case FMOD_ERR_HTTP -> "A HTTP error occurred. This is a catch-all for HTTP errors not listed elsewhere.";
            case FMOD_ERR_HTTP_ACCESS -> "The specified resource requires authentication or is forbidden.";
            case FMOD_ERR_HTTP_PROXY_AUTH -> "Proxy authentication is required to access the specified resource.";
            case FMOD_ERR_HTTP_SERVER_ERROR -> "A HTTP server error occurred.";
            case FMOD_ERR_HTTP_TIMEOUT -> "The HTTP request timed out.";
            case FMOD_ERR_INITIALIZATION -> "FMOD was not initialized correctly to support this function.";
            case FMOD_ERR_INITIALIZED -> "Cannot call this command after System::init.";
            case FMOD_ERR_INTERNAL ->
                    "An error occured in the FMOD system. Use the logging version of FMOD for more information.";
            case FMOD_ERR_INVALID_FLOAT -> "Value passed in was a NaN, Inf or denormalized float.";
            case FMOD_ERR_INVALID_HANDLE -> "An invalid object handle was used.";
            case FMOD_ERR_INVALID_PARAM -> "An invalid parameter was passed to this function.";
            case FMOD_ERR_INVALID_POSITION -> "An invalid seek position was passed to this function.";
            case FMOD_ERR_INVALID_SPEAKER ->
                    "An invalid speaker was passed to this function based on the current speaker mode.";
            case FMOD_ERR_INVALID_SYNCPOINT -> "The syncpoint did not come from this sound handle.";
            case FMOD_ERR_INVALID_THREAD -> "Tried to call a function on a thread that is not supported.";
            case FMOD_ERR_INVALID_VECTOR -> "The vectors passed in are not unit length, or perpendicular.";
            case FMOD_ERR_MAXAUDIBLE -> "Reached maximum audible playback count for this sound's soundgroup.";
            case FMOD_ERR_MEMORY -> "Not enough memory or resources.";
            case FMOD_ERR_MEMORY_CANTPOINT ->
                    "Can't use FMOD_OPENMEMORY_POINT on non PCM source data, or non mp3/xma/adpcm data if FMOD_CREATECOMPRESSEDSAMPLE was used.";
            case FMOD_ERR_NEEDS3D -> "Tried to call a command on a 2d sound when the command was meant for 3d sound.";
            case FMOD_ERR_NEEDSHARDWARE -> "Tried to use a feature that requires hardware support.";
            case FMOD_ERR_NET_CONNECT -> "Couldn't connect to the specified host.";
            case FMOD_ERR_NET_SOCKET_ERROR ->
                    "A socket error occurred.  This is a catch-all for socket-related errors not listed elsewhere.";
            case FMOD_ERR_NET_URL -> "The specified URL couldn't be resolved.";
            case FMOD_ERR_NET_WOULD_BLOCK -> "Operation on a non-blocking socket could not complete immediately.";
            case FMOD_ERR_NOTREADY ->
                    "Operation could not be performed because specified sound/DSP connection is not ready.";
            case FMOD_ERR_OUTPUT_ALLOCATED ->
                    "Error initializing output device, but more specifically, the output device is already in use and cannot be reused.";
            case FMOD_ERR_OUTPUT_CREATEBUFFER -> "Error creating hardware sound buffer.";
            case FMOD_ERR_OUTPUT_DRIVERCALL ->
                    "A call to a standard soundcard driver failed, which could possibly mean a bug in the driver or resources were missing or exhausted.";
            case FMOD_ERR_OUTPUT_FORMAT -> "Soundcard does not support the specified format.";
            case FMOD_ERR_OUTPUT_INIT -> "Error initializing output device.";
            case FMOD_ERR_OUTPUT_NODRIVERS ->
                    "The output device has no drivers installed.  If pre-init, FMOD_OUTPUT_NOSOUND is selected as the output mode.  If post-init, the function just fails.";
            case FMOD_ERR_PLUGIN -> "An unspecified error has been returned from a plugin.";
            case FMOD_ERR_PLUGIN_MISSING -> "A requested output, dsp unit type or codec was not available.";
            case FMOD_ERR_PLUGIN_RESOURCE ->
                    "A resource that the plugin requires cannot be allocated or found. (ie the DLS file for MIDI playback)";
            case FMOD_ERR_PLUGIN_VERSION -> "A plugin was built with an unsupported SDK version.";
            case FMOD_ERR_RECORD -> "An error occurred trying to initialize the recording device.";
            case FMOD_ERR_REVERB_CHANNELGROUP ->
                    "Reverb properties cannot be set on this channel because a parent channelgroup owns the reverb connection.";
            case FMOD_ERR_REVERB_INSTANCE ->
                    "Specified instance in FMOD_REVERB_PROPERTIES couldn't be set. Most likely because it is an invalid instance number or the reverb doesn't exist.";
            case FMOD_ERR_SUBSOUNDS ->
                    "The error occurred because the sound referenced contains subsounds when it shouldn't have, or it doesn't contain subsounds when it should have.  The operation may also not be able to be performed on a parent sound.";
            case FMOD_ERR_SUBSOUND_ALLOCATED ->
                    "This subsound is already being used by another sound, you cannot have more than one parent to a sound.  Null out the other parent's entry first.";
            case FMOD_ERR_SUBSOUND_CANTMOVE ->
                    "Shared subsounds cannot be replaced or moved from their parent stream, such as when the parent stream is an FSB file.";
            case FMOD_ERR_TAGNOTFOUND -> "The specified tag could not be found or there are no tags.";
            case FMOD_ERR_TOOMANYCHANNELS ->
                    "The sound created exceeds the allowable input channel count.  This can be increased using the 'maxinputchannels' parameter in System::setSoftwareFormat.";
            case FMOD_ERR_TRUNCATED ->
                    "The retrieved string is too long to fit in the supplied buffer and has been truncated.";
            case FMOD_ERR_UNIMPLEMENTED ->
                    "Something in FMOD hasn't been implemented when it should be. Contact support.";
            case FMOD_ERR_UNINITIALIZED ->
                    "This command failed because System::init or System::setDriver was not called.";
            case FMOD_ERR_UNSUPPORTED ->
                    "A command issued was not supported by this object.  Possibly a plugin without certain callbacks specified.";
            case FMOD_ERR_VERSION -> "The version number of this file format is not supported.";
            case FMOD_ERR_EVENT_ALREADY_LOADED -> "The specified bank has already been loaded.";
            case FMOD_ERR_EVENT_LIVEUPDATE_BUSY ->
                    "The live update connection failed due to the game already being connected.";
            case FMOD_ERR_EVENT_LIVEUPDATE_MISMATCH ->
                    "The live update connection failed due to the game data being out of sync with the tool.";
            case FMOD_ERR_EVENT_LIVEUPDATE_TIMEOUT -> "The live update connection timed out.";
            case FMOD_ERR_EVENT_NOTFOUND -> "The requested event, parameter, bus or vca could not be found.";
            case FMOD_ERR_STUDIO_UNINITIALIZED -> "The Studio::System object is not yet initialized.";
            case FMOD_ERR_STUDIO_NOT_LOADED -> "The specified resource is not loaded, so it can't be unloaded.";
            case FMOD_ERR_INVALID_STRING -> "An invalid string was passed to this function.";
            case FMOD_ERR_ALREADY_LOCKED -> "The specified resource is already locked.";
            case FMOD_ERR_NOT_LOCKED -> "The specified resource is not locked, so it can't be unlocked.";
            case FMOD_ERR_RECORD_DISCONNECTED -> "The specified recording driver has been disconnected.";
            case FMOD_ERR_TOOMANYSAMPLES -> "The length provided exceeds the allowable limit.";
            default -> "Unknown error.";
        };
    }

    public int getCode() {
        return code;
    }
}