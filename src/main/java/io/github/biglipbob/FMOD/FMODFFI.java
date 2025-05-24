package io.github.biglipbob.FMOD;

import com.sun.jna.ptr.*;
import com.sun.jna.*;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
interface FMODFFI extends Library {
    int FMOD_System_Create(PointerByReference system, int headerVersion);

    int FMOD_System_Init(Pointer system, int maxChannels, int flags, Pointer extraDriverData);

    int FMOD_System_Update(Pointer system);

    int FMOD_System_Close(Pointer system);

    int FMOD_System_Release(Pointer system);

    int FMOD_Debug_Initialize(int flags, int mode, FMODDebugCallbackFFI callback, String filename);

    int FMOD_Thread_SetAttributes(int type, int affinity, int priority, int stackSize);

    int FMOD_Memory_Initialize(Pointer poolMem, int poolLen, FMODMemoryAllocCallbackFFI userAlloc, FMODMemoryReallocCallbackFFI userRealloc, FMODMemoryFreeCallbackFFI userFree, int memTypeFlags);

    int FMOD_Memory_GetStats(IntByReference currentAlloced, IntByReference maxAlloced, int blockingBool);

    int FMOD_File_SetDiskBusy(int busy);

    int FMOD_File_GetDiskBusy(IntByReference busy);

    int FMOD_System_SetOutput(Pointer system, int output);

    int FMOD_System_GetOutput(Pointer system, IntByReference output);

    int FMOD_System_GetNumDrivers(Pointer system, IntByReference numDrivers);

    int FMOD_System_GetDriverInfo(Pointer system, int id, Pointer name, int nameLen, Pointer guid, IntByReference systemRate, IntByReference speakerMode, IntByReference speakerModeChannels);

    int FMOD_System_SetDriver(Pointer system, int driver);

    int FMOD_System_GetDriver(Pointer system, IntByReference driver);

    int FMOD_System_SetSoftwareChannels(Pointer system, int numSoftwareChannels);

    int FMOD_System_GetSoftwareChannels(Pointer system, IntByReference numSoftwareChannels);

    int FMOD_System_SetSoftwareFormat(Pointer system, int sampleRate, int speakerMode, int numRawSpeakers);

    int FMOD_System_GetSoftwareFormat(Pointer system, IntByReference sampleRate, IntByReference speakerMode, IntByReference numRawSpeakers);

    int FMOD_System_SetDSPBufferSize(Pointer system, int bufferLength, int numBuffers);

    int FMOD_System_GetDSPBufferSize(Pointer system, IntByReference bufferLength, IntByReference numBuffers);

    int FMOD_System_SetFileSystem(Pointer system, FMODFileOpenCallbackFFI userOpen, FMODFileCloseCallbackFFI userClose, FMODFileReadCallbackFFI userRead, FMODFileSeekCallbackFFI userSeek, FMODFileAsyncReadCallbackFFI userAsyncRead, FMODFileAsyncCancelCallbackFFI userAsyncCancel, int blockAlign);

    int FMOD_System_AttachFileSystem(Pointer system, FMODFileOpenCallbackFFI userOpen, FMODFileCloseCallbackFFI userClose, FMODFileReadCallbackFFI userRead, FMODFileSeekCallbackFFI userSeek);

    int FMOD_System_SetAdvancedSettings(Pointer system, Pointer settings);

    int FMOD_System_GetAdvancedSettings(Pointer system, Pointer settings);

    int FMOD_System_SetCallback(Pointer system, FMODSystemCallbackFFI callback, int callbackMask);

    int FMOD_System_SetPluginPath(Pointer system, String path);

    int FMOD_System_LoadPlugin(Pointer system, String filename, IntByReference handle, int priority);

    int FMOD_System_UnloadPlugin(Pointer system, int handle);

    int FMOD_System_GetNumNestedPlugins(Pointer system, int handle, IntByReference count);

    int FMOD_System_GetNestedPlugin(Pointer system, int handle, int index, IntByReference nestedHandle);

    int FMOD_System_GetNumPlugins(Pointer system, int pluginType, IntByReference numPlugins);

    int FMOD_System_GetPluginHandle(Pointer system, int pluginType, int index, IntByReference handle);

    int FMOD_System_GetPluginInfo(Pointer system, int handle, IntByReference pluginType, Pointer name, int nameLen, IntByReference version);

    int FMOD_System_SetOutputByPlugin(Pointer system, int handle);

    int FMOD_System_GetOutputByPlugin(Pointer system, IntByReference handle);

    int FMOD_System_CreateDSPByPlugin(Pointer system, int handle, PointerByReference dsp);

    int FMOD_System_GetDSPInfoByPlugin(Pointer system, int handle, PointerByReference description);

    int FMOD_System_RegisterCodec(Pointer system, Pointer description, IntByReference handle, int priority);

    int FMOD_System_RegisterDSP(Pointer system, Pointer description, IntByReference handle);

    int FMOD_System_RegisterOutput(Pointer system, Pointer description, IntByReference handle);

    int FMOD_System_SetSpeakerPosition(Pointer system, int speaker, float x, float y, int activeBool);

    int FMOD_System_GetSpeakerPosition(Pointer system, int speaker, FloatByReference x, FloatByReference y, IntByReference activeBool);

    int FMOD_System_SetStreamBufferSize(Pointer system, int fileBufferSize, int fileBufferSizeType);

    int FMOD_System_GetStreamBufferSize(Pointer system, IntByReference fileBufferSize, IntByReference fileBufferSizeType);

    int FMOD_System_Set3DSettings(Pointer system, float dopplerScale, float distanceFactor, float rolloffScale);

    int FMOD_System_Get3DSettings(Pointer system, FloatByReference dopplerScale, FloatByReference distanceFactor, FloatByReference rolloffScale);

    int FMOD_System_Set3DNumListeners(Pointer system, int numListeners);

    int FMOD_System_Get3DNumListeners(Pointer system, IntByReference numListeners);

    int FMOD_System_Set3DListenerAttributes(Pointer system, int listener, Pointer pos, Pointer vel, Pointer forward, Pointer up);

    int FMOD_System_Get3DListenerAttributes(Pointer system, int listener, Pointer pos, Pointer vel, Pointer forward, Pointer up);

    int FMOD_System_Set3DRolloffCallback(Pointer system, FMOD3DRolloffCallbackFFI callback);

    int FMOD_System_MixerSuspend(Pointer system);

    int FMOD_System_MixerResume(Pointer system);

    int FMOD_System_GetDefaultMixMatrix(Pointer system, int sourceSpeakerMode, int targetSpeakerMode, Pointer matrix, int matrixHop);

    int FMOD_System_GetSpeakerModeChannels(Pointer system, int mode, IntByReference channels);

    int FMOD_System_GetVersion(Pointer system, IntByReference version, IntByReference buildNumber);

    int FMOD_System_GetOutputHandle(Pointer system, PointerByReference handle);

    int FMOD_System_GetChannelsPlaying(Pointer system, IntByReference channels, IntByReference realChannels);

    int FMOD_System_GetCPUUsage(Pointer system, Pointer usage);

    int FMOD_System_GetFileUsage(Pointer system, LongByReference sampleBytesRead, LongByReference streamBytesRead, LongByReference otherBytesRead);

    int FMOD_System_CreateSound(Pointer system, Pointer data, int mode, Pointer exinfo, PointerByReference sound);
    int FMOD_System_CreateSound(Pointer system, String name, int mode, Pointer exinfo, PointerByReference sound);

    int FMOD_System_CreateStream(Pointer system, Pointer data, int mode, Pointer exinfo, PointerByReference sound);
    int FMOD_System_CreateStream(Pointer system, String name, int mode, Pointer exinfo, PointerByReference sound);

    int FMOD_System_CreateDSP(Pointer system, Pointer description, PointerByReference dsp);

    int FMOD_System_CreateDSPByType(Pointer system, int type, PointerByReference dsp);

    int FMOD_System_CreateChannelGroup(Pointer system, String name, PointerByReference channelGroup);

    int FMOD_System_CreateSoundGroup(Pointer system, String name, PointerByReference soundGroup);

    int FMOD_System_CreateReverb3D(Pointer system, PointerByReference reverb);

    int FMOD_System_PlaySound(Pointer system, Pointer sound, Pointer channelGroup, int pausedBool, PointerByReference channel);

    int FMOD_System_PlayDSP(Pointer system, Pointer dsp, Pointer channelGroup, int pausedBool, PointerByReference channel);

    int FMOD_System_GetChannel(Pointer system, int channelId, PointerByReference channel);

    int FMOD_System_GetDSPInfoByType(Pointer system, int type, PointerByReference description);

    int FMOD_System_GetMasterChannelGroup(Pointer system, PointerByReference channelGroup);

    int FMOD_System_GetMasterSoundGroup(Pointer system, PointerByReference soundGroup);

    int FMOD_System_AttachChannelGroupToPort(Pointer system, int portType, int portIndex, Pointer channelGroup, int passThruBool);

    int FMOD_System_DetachChannelGroupFromPort(Pointer system, Pointer channelGroup);

    int FMOD_System_SetReverbProperties(Pointer system, int instance, Pointer prop);

    int FMOD_System_GetReverbProperties(Pointer system, int instance, Pointer prop);

    int FMOD_System_LockDSP(Pointer system);

    int FMOD_System_UnlockDSP(Pointer system);

    int FMOD_System_GetRecordNumDrivers(Pointer system, IntByReference numDrivers, IntByReference numConnected);

    int FMOD_System_GetRecordDriverInfo(Pointer system, int id, Pointer name, int nameLen, Pointer guid, IntByReference systemRate, IntByReference speakerMode, IntByReference speakerModeChannels, Pointer state);

    int FMOD_System_GetRecordPosition(Pointer system, int id, IntByReference position);

    int FMOD_System_RecordStart(Pointer system, int id, Pointer sound, int loopBool);

    int FMOD_System_RecordStop(Pointer system, int id);

    int FMOD_System_IsRecording(Pointer system, int id, IntByReference recordingBool);

    int FMOD_System_CreateGeometry(Pointer system, int maxPolygons, int maxVertices, PointerByReference geometry);

    int FMOD_System_SetGeometrySettings(Pointer system, float maxWorldSize);

    int FMOD_System_GetGeometrySettings(Pointer system, FloatByReference maxWorldSize);

    int FMOD_System_LoadGeometry(Pointer system, Pointer data, int dataSize, PointerByReference geometry);

    int FMOD_System_GetGeometryOcclusion(Pointer system, Pointer listener, Pointer source, FloatByReference direct, FloatByReference reverb);

    int FMOD_System_SetNetworkProxy(Pointer system, String proxy);

    int FMOD_System_GetNetworkProxy(Pointer system, Pointer proxy, int proxyLen);

    int FMOD_System_SetNetworkTimeout(Pointer system, int timeout);

    int FMOD_System_GetNetworkTimeout(Pointer system, IntByReference timeout);

    int FMOD_System_SetUserData(Pointer system, Pointer userData);

    int FMOD_System_GetUserData(Pointer system, PointerByReference userData);

    int FMOD_Sound_Release(Pointer sound);

    int FMOD_Sound_GetSystemObject(Pointer sound, PointerByReference system);

    int FMOD_Sound_Lock(Pointer sound, int offset, int length, PointerByReference ptr1, PointerByReference ptr2, IntByReference len1, IntByReference len2);

    int FMOD_Sound_Unlock(Pointer sound, Pointer ptr1, Pointer ptr2, int len1, int len2);

    int FMOD_Sound_SetDefaults(Pointer sound, float frequency, int priority);

    int FMOD_Sound_GetDefaults(Pointer sound, FloatByReference frequency, IntByReference priority);

    int FMOD_Sound_Set3DMinMaxDistance(Pointer sound, float min, float max);

    int FMOD_Sound_Get3DMinMaxDistance(Pointer sound, FloatByReference min, FloatByReference max);

    int FMOD_Sound_Set3DConeSettings(Pointer sound, float insideConeAngle, float outsideConeAngle, float outsideVolume);

    int FMOD_Sound_Get3DConeSettings(Pointer sound, FloatByReference insideConeAngle, FloatByReference outsideConeAngle, FloatByReference outsideVolume);

    int FMOD_Sound_Set3DCustomRolloff(Pointer sound, Pointer points, int numPoints);

    int FMOD_Sound_Get3DCustomRolloff(Pointer sound, PointerByReference points, IntByReference numPoints);

    int FMOD_Sound_GetSubSound(Pointer sound, int index, PointerByReference subSound);

    int FMOD_Sound_GetSubSoundParent(Pointer sound, PointerByReference parentSound);

    int FMOD_Sound_GetName(Pointer sound, Pointer name, int nameLen);

    int FMOD_Sound_GetLength(Pointer sound, IntByReference length, int lengthType);

    int FMOD_Sound_GetFormat(Pointer sound, IntByReference type, IntByReference format, IntByReference channels, IntByReference bits);

    int FMOD_Sound_GetNumSubSounds(Pointer sound, IntByReference numSubSounds);

    int FMOD_Sound_GetNumTags(Pointer sound, IntByReference numTags, IntByReference numTagsUpdated);

    int FMOD_Sound_GetTag(Pointer sound, String name, int index, Pointer tag);

    int FMOD_Sound_GetOpenState(Pointer sound, IntByReference openState, IntByReference percentBuffered, IntByReference starvingBool, IntByReference diskBusyBool);

    int FMOD_Sound_ReadData(Pointer sound, Pointer buffer, int length, IntByReference read);

    int FMOD_Sound_SeekData(Pointer sound, int pcm);

    int FMOD_Sound_SetSoundGroup(Pointer sound, Pointer soundGroup);

    int FMOD_Sound_GetSoundGroup(Pointer sound, PointerByReference soundGroup);

    int FMOD_Sound_GetNumSyncPoints(Pointer sound, IntByReference numSyncPoints);

    int FMOD_Sound_GetSyncPoint(Pointer sound, int index, PointerByReference point);

    int FMOD_Sound_GetSyncPointInfo(Pointer sound, Pointer point, Pointer name, int nameLen, IntByReference offset, int offsetType);

    int FMOD_Sound_AddSyncPoint(Pointer sound, int offset, int offsetType, String name, PointerByReference point);

    int FMOD_Sound_DeleteSyncPoint(Pointer sound, Pointer point);

    int FMOD_Sound_SetMode(Pointer sound, int mode);

    int FMOD_Sound_GetMode(Pointer sound, IntByReference mode);

    int FMOD_Sound_SetLoopCount(Pointer sound, int loopCount);

    int FMOD_Sound_GetLoopCount(Pointer sound, IntByReference loopCount);

    int FMOD_Sound_SetLoopPoints(Pointer sound, int loopStart, int loopStartType, int loopEnd, int loopEndType);

    int FMOD_Sound_GetLoopPoints(Pointer sound, IntByReference loopStart, IntByReference loopStartType, IntByReference loopEnd, IntByReference loopEndType);

    int FMOD_Sound_GetMusicNumChannels(Pointer sound, IntByReference numChannels);

    int FMOD_Sound_SetMusicChannelVolume(Pointer sound, int channel, float volume);

    int FMOD_Sound_GetMusicChannelVolume(Pointer sound, int channel, FloatByReference volume);

    int FMOD_Sound_SetMusicSpeed(Pointer sound, float speed);

    int FMOD_Sound_GetMusicSpeed(Pointer sound, FloatByReference speed);

    int FMOD_Sound_SetUserData(Pointer sound, Pointer userData);

    int FMOD_Sound_GetUserData(Pointer sound, PointerByReference userData);

    int FMOD_Channel_GetSystemObject(Pointer channel, PointerByReference system);

    int FMOD_Channel_Stop(Pointer channel);

    int FMOD_Channel_SetPaused(Pointer channel, int pausedBool);

    int FMOD_Channel_GetPaused(Pointer channel, IntByReference pausedBool);

    int FMOD_Channel_SetVolume(Pointer channel, float volume);

    int FMOD_Channel_GetVolume(Pointer channel, FloatByReference volume);

    int FMOD_Channel_SetVolumeRamp(Pointer channel, int rampBool);

    int FMOD_Channel_GetVolumeRamp(Pointer channel, IntByReference rampBool);

    int FMOD_Channel_GetAudibility(Pointer channel, FloatByReference audibility);

    int FMOD_Channel_SetPitch(Pointer channel, float pitch);

    int FMOD_Channel_GetPitch(Pointer channel, FloatByReference pitch);

    int FMOD_Channel_SetMute(Pointer channel, int muteBool);

    int FMOD_Channel_GetMute(Pointer channel, IntByReference muteBool);

    int FMOD_Channel_SetReverbProperties(Pointer channel, int instance, float wet);

    int FMOD_Channel_GetReverbProperties(Pointer channel, int instance, FloatByReference wet);

    int FMOD_Channel_SetLowPassGain(Pointer channel, float gain);

    int FMOD_Channel_GetLowPassGain(Pointer channel, FloatByReference gain);

    int FMOD_Channel_SetMode(Pointer channel, int mode);

    int FMOD_Channel_GetMode(Pointer channel, IntByReference mode);

    int FMOD_Channel_SetCallback(Pointer channel, FMODChannelCallbackFFI callback);

    int FMOD_Channel_IsPlaying(Pointer channel, IntByReference isPlayingBool);

    int FMOD_Channel_SetPan(Pointer channel, float pan);

    int FMOD_Channel_SetMixLevelsOutput(Pointer channel, float frontLeft, float frontRight, float center, float lfe, float surroundLeft, float surroundRight, float backLeft, float backRight);

    int FMOD_Channel_SetMixLevelsInput(Pointer channel, float[] levels, int numLevels);

    int FMOD_Channel_SetMixMatrix(Pointer channel, Pointer matrix, int outChannels, int inChannels, int inChannelHop);

    int FMOD_Channel_GetMixMatrix(Pointer channel, Pointer matrix, IntByReference outChannels, IntByReference inChannels, int inChannelHop);

    int FMOD_Channel_GetDSPClock(Pointer channel, LongByReference dspClock, LongByReference parentClock);

    int FMOD_Channel_SetDelay(Pointer channel, long dspClockStart, long dspClockEnd, int stopChannelsBool);

    int FMOD_Channel_GetDelay(Pointer channel, LongByReference dspClockStart, LongByReference dspClockEnd, IntByReference stopChannels);

    int FMOD_Channel_AddFadePoint(Pointer channel, long dspClock, float volume);

    int FMOD_Channel_SetFadePointRamp(Pointer channel, long dspClock, float volume);

    int FMOD_Channel_RemoveFadePoints(Pointer channel, long dspClockStart, long dspClockEnd);

    int FMOD_Channel_GetFadePoints(Pointer channel, IntByReference numPoints, Pointer pointDspClock, Pointer pointVolume);

    int FMOD_Channel_GetDSP(Pointer channel, int index, PointerByReference dsp);

    int FMOD_Channel_AddDSP(Pointer channel, int index, Pointer dsp);

    int FMOD_Channel_RemoveDSP(Pointer channel, Pointer dsp);

    int FMOD_Channel_GetNumDSPs(Pointer channel, IntByReference numDsps);

    int FMOD_Channel_SetDSPIndex(Pointer channel, Pointer dsp, int index);

    int FMOD_Channel_GetDSPIndex(Pointer channel, Pointer dsp, IntByReference index);

    int FMOD_Channel_Set3DAttributes(Pointer channel, Pointer pos, Pointer vel);

    int FMOD_Channel_Get3DAttributes(Pointer channel, Pointer pos, Pointer vel);

    int FMOD_Channel_Set3DMinMaxDistance(Pointer channel, float min, float max);

    int FMOD_Channel_Get3DMinMaxDistance(Pointer channel, FloatByReference min, FloatByReference max);

    int FMOD_Channel_Set3DConeSettings(Pointer channel, float insideConeAngle, float outsideConeAngle, float outsideVolume);

    int FMOD_Channel_Get3DConeSettings(Pointer channel, FloatByReference insideConeAngle, FloatByReference outsideConeAngle, FloatByReference outsideVolume);

    int FMOD_Channel_Set3DConeOrientation(Pointer channel, Pointer orientation);

    int FMOD_Channel_Get3DConeOrientation(Pointer channel, Pointer orientation);

    int FMOD_Channel_Set3DCustomRolloff(Pointer channel, Pointer points, int numPoints);

    int FMOD_Channel_Get3DCustomRolloff(Pointer channel, PointerByReference points, IntByReference numPoints);

    int FMOD_Channel_Set3DOcclusion(Pointer channel, float directOcclusion, float reverbOcclusion);

    int FMOD_Channel_Get3DOcclusion(Pointer channel, FloatByReference directOcclusion, FloatByReference reverbOcclusion);

    int FMOD_Channel_Set3DSpread(Pointer channel, float angle);

    int FMOD_Channel_Get3DSpread(Pointer channel, FloatByReference angle);

    int FMOD_Channel_Set3DLevel(Pointer channel, float level);

    int FMOD_Channel_Get3DLevel(Pointer channel, FloatByReference level);

    int FMOD_Channel_Set3DDopplerLevel(Pointer channel, float level);

    int FMOD_Channel_Get3DDopplerLevel(Pointer channel, FloatByReference level);

    int FMOD_Channel_Set3DDistanceFilter(Pointer channel, int customBool, float customLevel, float centerFreq);

    int FMOD_Channel_Get3DDistanceFilter(Pointer channel, IntByReference customBool, FloatByReference customLevel, FloatByReference centerFreq);

    int FMOD_Channel_SetUserData(Pointer channel, Pointer userData);

    int FMOD_Channel_GetUserData(Pointer channel, PointerByReference userData);

    int FMOD_Channel_SetFrequency(Pointer channel, float frequency);

    int FMOD_Channel_GetFrequency(Pointer channel, FloatByReference frequency);

    int FMOD_Channel_SetPriority(Pointer channel, int priority);

    int FMOD_Channel_GetPriority(Pointer channel, IntByReference priority);

    int FMOD_Channel_SetPosition(Pointer channel, int position, int posType);

    int FMOD_Channel_GetPosition(Pointer channel, IntByReference position, int posType);

    int FMOD_Channel_SetChannelGroup(Pointer channel, Pointer channelGroup);

    int FMOD_Channel_GetChannelGroup(Pointer channel, PointerByReference channelGroup);

    int FMOD_Channel_SetLoopCount(Pointer channel, int loopCount);

    int FMOD_Channel_GetLoopCount(Pointer channel, IntByReference loopCount);

    int FMOD_Channel_SetLoopPoints(Pointer channel, int loopStart, int loopStartType, int loopEnd, int loopEndType);

    int FMOD_Channel_GetLoopPoints(Pointer channel, IntByReference loopStart, int loopStartType, IntByReference loopEnd, int loopEndType);

    int FMOD_Channel_IsVirtual(Pointer channel, IntByReference isVirtual);

    int FMOD_Channel_GetCurrentSound(Pointer channel, PointerByReference sound);

    int FMOD_Channel_GetIndex(Pointer channel, IntByReference index);

    int FMOD_ChannelGroup_GetSystemObject(Pointer channelGroup, PointerByReference system);

    int FMOD_ChannelGroup_Stop(Pointer channelGroup);

    int FMOD_ChannelGroup_SetPaused(Pointer channelGroup, int pausedBool);

    int FMOD_ChannelGroup_GetPaused(Pointer channelGroup, IntByReference pausedBool);

    int FMOD_ChannelGroup_SetVolume(Pointer channelGroup, float volume);

    int FMOD_ChannelGroup_GetVolume(Pointer channelGroup, FloatByReference volume);

    int FMOD_ChannelGroup_SetVolumeRamp(Pointer channelGroup, int rampBool);

    int FMOD_ChannelGroup_GetVolumeRamp(Pointer channelGroup, IntByReference rampBool);

    int FMOD_ChannelGroup_GetAudibility(Pointer channelGroup, FloatByReference audibility);

    int FMOD_ChannelGroup_SetPitch(Pointer channelGroup, float pitch);

    int FMOD_ChannelGroup_GetPitch(Pointer channelGroup, FloatByReference pitch);

    int FMOD_ChannelGroup_SetMute(Pointer channelGroup, int muteBool);

    int FMOD_ChannelGroup_GetMute(Pointer channelGroup, IntByReference muteBool);

    int FMOD_ChannelGroup_SetReverbProperties(Pointer channelGroup, int instance, float wet);

    int FMOD_ChannelGroup_GetReverbProperties(Pointer channelGroup, int instance, FloatByReference wet);

    int FMOD_ChannelGroup_SetLowPassGain(Pointer channelGroup, float gain);

    int FMOD_ChannelGroup_GetLowPassGain(Pointer channelGroup, FloatByReference gain);

    int FMOD_ChannelGroup_SetMode(Pointer channelGroup, int mode);

    int FMOD_ChannelGroup_GetMode(Pointer channelGroup, IntByReference mode);

    int FMOD_ChannelGroup_SetCallback(Pointer channelGroup, FMODChannelGroupCallbackFFI callback);

    int FMOD_ChannelGroup_IsPlaying(Pointer channelGroup, IntByReference isPlayingBool);

    int FMOD_ChannelGroup_SetPan(Pointer channelGroup, float pan);

    int FMOD_ChannelGroup_SetMixLevelsOutput(Pointer channelGroup, float frontLeft, float frontRight, float center, float lfe, float surroundLeft, float surroundRight, float backLeft, float backRight);

    int FMOD_ChannelGroup_SetMixLevelsInput(Pointer channelGroup, float[] levels, int numLevels);

    int FMOD_ChannelGroup_SetMixMatrix(Pointer channelGroup, Pointer matrix, int outChannels, int inChannels, int inChannelHop);

    int FMOD_ChannelGroup_GetMixMatrix(Pointer channelGroup, Pointer matrix, IntByReference outChannels, IntByReference inChannels, int inChannelHop);

    int FMOD_ChannelGroup_GetDSPClock(Pointer channelGroup, LongByReference dspClock, LongByReference parentClock);

    int FMOD_ChannelGroup_SetDelay(Pointer channelGroup, long dspClockStart, long dspClockEnd, int stopChannelsBool);

    int FMOD_ChannelGroup_GetDelay(Pointer channelGroup, LongByReference dspClockStart, LongByReference dspClockEnd, IntByReference stopChannels);

    int FMOD_ChannelGroup_AddFadePoint(Pointer channelGroup, long dspClock, float volume);

    int FMOD_ChannelGroup_SetFadePointRamp(Pointer channelGroup, long dspClock, float volume);

    int FMOD_ChannelGroup_RemoveFadePoints(Pointer channelGroup, long dspClockStart, long dspClockEnd);

    int FMOD_ChannelGroup_GetFadePoints(Pointer channelGroup, IntByReference numPoints, Pointer pointDspClock, Pointer pointVolume);

    int FMOD_ChannelGroup_GetDSP(Pointer channelGroup, int index, PointerByReference dsp);

    int FMOD_ChannelGroup_AddDSP(Pointer channelGroup, int index, Pointer dsp);

    int FMOD_ChannelGroup_RemoveDSP(Pointer channelGroup, Pointer dsp);

    int FMOD_ChannelGroup_GetNumDSPs(Pointer channelGroup, IntByReference numDsps);

    int FMOD_ChannelGroup_SetDSPIndex(Pointer channelGroup, Pointer dsp, int index);

    int FMOD_ChannelGroup_GetDSPIndex(Pointer channelGroup, Pointer dsp, IntByReference index);

    int FMOD_ChannelGroup_Set3DAttributes(Pointer channelGroup, Pointer pos, Pointer vel);

    int FMOD_ChannelGroup_Get3DAttributes(Pointer channelGroup, Pointer pos, Pointer vel);

    int FMOD_ChannelGroup_Set3DMinMaxDistance(Pointer channelGroup, float min, float max);

    int FMOD_ChannelGroup_Get3DMinMaxDistance(Pointer channelGroup, FloatByReference min, FloatByReference max);

    int FMOD_ChannelGroup_Set3DConeSettings(Pointer channelGroup, float insideConeAngle, float outsideConeAngle, float outsideVolume);

    int FMOD_ChannelGroup_Get3DConeSettings(Pointer channelGroup, FloatByReference insideConeAngle, FloatByReference outsideConeAngle, FloatByReference outsideVolume);

    int FMOD_ChannelGroup_Set3DConeOrientation(Pointer channelGroup, Pointer orientation);

    int FMOD_ChannelGroup_Get3DConeOrientation(Pointer channelGroup, Pointer orientation);

    int FMOD_ChannelGroup_Set3DCustomRolloff(Pointer channelGroup, Pointer points, int numPoints);

    int FMOD_ChannelGroup_Get3DCustomRolloff(Pointer channelGroup, PointerByReference points, IntByReference numPoints);

    int FMOD_ChannelGroup_Set3DOcclusion(Pointer channelGroup, float directOcclusion, float reverbOcclusion);

    int FMOD_ChannelGroup_Get3DOcclusion(Pointer channelGroup, FloatByReference directOcclusion, FloatByReference reverbOcclusion);

    int FMOD_ChannelGroup_Set3DSpread(Pointer channelGroup, float angle);

    int FMOD_ChannelGroup_Get3DSpread(Pointer channelGroup, FloatByReference angle);

    int FMOD_ChannelGroup_Set3DLevel(Pointer channelGroup, float level);

    int FMOD_ChannelGroup_Get3DLevel(Pointer channelGroup, FloatByReference level);

    int FMOD_ChannelGroup_Set3DDopplerLevel(Pointer channelGroup, float level);

    int FMOD_ChannelGroup_Get3DDopplerLevel(Pointer channelGroup, FloatByReference level);

    int FMOD_ChannelGroup_Set3DDistanceFilter(Pointer channelGroup, int customBool, float customLevel, float centerFreq);

    int FMOD_ChannelGroup_Get3DDistanceFilter(Pointer channelGroup, IntByReference customBool, FloatByReference customLevel, FloatByReference centerFreq);

    int FMOD_ChannelGroup_SetUserData(Pointer channelGroup, Pointer userData);

    int FMOD_ChannelGroup_GetUserData(Pointer channelGroup, PointerByReference userData);

    int FMOD_ChannelGroup_Release(Pointer channelGroup);

    int FMOD_ChannelGroup_AddGroup(Pointer channelGroup, Pointer group, int propagateDspClockBool, PointerByReference connection);

    int FMOD_ChannelGroup_GetNumGroups(Pointer channelGroup, IntByReference numGroups);

    int FMOD_ChannelGroup_GetGroup(Pointer channelGroup, int index, PointerByReference group);

    int FMOD_ChannelGroup_GetParentGroup(Pointer channelGroup, PointerByReference group);

    int FMOD_ChannelGroup_GetName(Pointer channelGroup, Pointer name, int nameLen);

    int FMOD_ChannelGroup_GetNumChannels(Pointer channelGroup, IntByReference numChannels);

    int FMOD_ChannelGroup_GetChannel(Pointer channelGroup, int index, PointerByReference channel);

    int FMOD_SoundGroup_Release(Pointer soundGroup);

    int FMOD_SoundGroup_GetSystemObject(Pointer soundGroup, PointerByReference system);

    int FMOD_SoundGroup_SetMaxAudible(Pointer soundGroup, int maxAudible);

    int FMOD_SoundGroup_GetMaxAudible(Pointer soundGroup, IntByReference maxAudible);

    int FMOD_SoundGroup_SetMaxAudibleBehavior(Pointer soundGroup, int behavior);

    int FMOD_SoundGroup_GetMaxAudibleBehavior(Pointer soundGroup, IntByReference behavior);

    int FMOD_SoundGroup_SetMuteFadeSpeed(Pointer soundGroup, float speed);

    int FMOD_SoundGroup_GetMuteFadeSpeed(Pointer soundGroup, FloatByReference speed);

    int FMOD_SoundGroup_SetVolume(Pointer soundGroup, float volume);

    int FMOD_SoundGroup_GetVolume(Pointer soundGroup, FloatByReference volume);

    int FMOD_SoundGroup_Stop(Pointer soundGroup);

    int FMOD_SoundGroup_GetName(Pointer soundGroup, Pointer name, int nameLen);

    int FMOD_SoundGroup_GetNumSounds(Pointer soundGroup, IntByReference numSounds);

    int FMOD_SoundGroup_GetSound(Pointer soundGroup, int index, PointerByReference sound);

    int FMOD_SoundGroup_GetNumPlaying(Pointer soundGroup, IntByReference numPlaying);

    int FMOD_SoundGroup_SetUserData(Pointer soundGroup, Pointer userData);

    int FMOD_SoundGroup_GetUserData(Pointer soundGroup, PointerByReference userData);

    int FMOD_DSP_Release(Pointer dsp);

    int FMOD_DSP_GetSystemObject(Pointer dsp, PointerByReference system);

    int FMOD_DSP_AddInput(Pointer dsp, Pointer input, PointerByReference connection, int type);

    int FMOD_DSP_DisconnectFrom(Pointer dsp, Pointer target, Pointer connection);

    int FMOD_DSP_DisconnectAll(Pointer dsp, int inputsBool, int outputsBool);

    int FMOD_DSP_GetNumInputs(Pointer dsp, IntByReference numInputs);

    int FMOD_DSP_GetNumOutputs(Pointer dsp, IntByReference numOutputs);

    int FMOD_DSP_GetInput(Pointer dsp, int index, PointerByReference input, PointerByReference inputConnection);

    int FMOD_DSP_GetOutput(Pointer dsp, int index, PointerByReference output, PointerByReference outputConnection);

    int FMOD_DSP_SetActive(Pointer dsp, int activeBool);

    int FMOD_DSP_GetActive(Pointer dsp, IntByReference activeBool);

    int FMOD_DSP_SetBypass(Pointer dsp, int bypassBool);

    int FMOD_DSP_GetBypass(Pointer dsp, IntByReference bypassBool);

    int FMOD_DSP_SetWetDryMix(Pointer dsp, float prewet, float postwet, float dry);

    int FMOD_DSP_GetWetDryMix(Pointer dsp, FloatByReference prewet, FloatByReference postwet, FloatByReference dry);

    int FMOD_DSP_SetChannelFormat(Pointer dsp, int channelMask, int numChannels, int sourceSpeakerMode);

    int FMOD_DSP_GetChannelFormat(Pointer dsp, IntByReference channelMask, IntByReference numChannels, IntByReference sourceSpeakerMode);

    int FMOD_DSP_GetOutputChannelFormat(Pointer dsp, int inMask, int inChannels, int inSpeakerMode, IntByReference outMask, IntByReference outChannels, IntByReference outSpeakerMode);

    int FMOD_DSP_Reset(Pointer dsp);

    int FMOD_DSP_SetCallback(Pointer dsp, FMODDSPCallbackFFI callback);

    int FMOD_DSP_SetParameterFloat(Pointer dsp, int index, float value);

    int FMOD_DSP_SetParameterInt(Pointer dsp, int index, int value);

    int FMOD_DSP_SetParameterBool(Pointer dsp, int index, int valueBool);

    int FMOD_DSP_SetParameterData(Pointer dsp, int index, Pointer data, int length);

    int FMOD_DSP_GetParameterFloat(Pointer dsp, int index, FloatByReference value, Pointer valueStr, int valueStrLen);

    int FMOD_DSP_GetParameterInt(Pointer dsp, int index, IntByReference value, Pointer valueStr, int valueStrLen);

    int FMOD_DSP_GetParameterBool(Pointer dsp, int index, IntByReference valueBool, Pointer valueStr, int valueStrLen);

    int FMOD_DSP_GetParameterData(Pointer dsp, int index, PointerByReference data, IntByReference length, Pointer valueStr, int valueStrLen);

    int FMOD_DSP_GetNumParameters(Pointer dsp, IntByReference numParams);

    int FMOD_DSP_GetParameterInfo(Pointer dsp, int index, PointerByReference desc);

    int FMOD_DSP_GetDataParameterIndex(Pointer dsp, int dataType, IntByReference index);

    int FMOD_DSP_ShowConfigDialog(Pointer dsp, Pointer hwnd, int showBool);

    int FMOD_DSP_GetInfo(Pointer dsp, Pointer name, IntByReference version, IntByReference channels, IntByReference configWidth, IntByReference configHeight);

    int FMOD_DSP_GetType(Pointer dsp, IntByReference type);

    int FMOD_DSP_GetIdle(Pointer dsp, IntByReference idle);

    int FMOD_DSP_SetUserData(Pointer dsp, Pointer userData);

    int FMOD_DSP_GetUserData(Pointer dsp, PointerByReference userData);

    int FMOD_DSP_SetMeteringEnabled(Pointer dsp, int inputEnabledBool, int outputEnabledBool);

    int FMOD_DSP_GetMeteringEnabled(Pointer dsp, IntByReference inputEnabledBool, IntByReference outputEnabledBool);

    int FMOD_DSP_GetMeteringInfo(Pointer dsp, Pointer inputInfo, Pointer outputInfo);

    int FMOD_DSP_GetCPUUsage(Pointer dsp, IntByReference exclusiveBool, IntByReference inclusiveBool);

    int FMOD_DSPConnection_GetInput(Pointer dspConnection, PointerByReference input);

    int FMOD_DSPConnection_GetOutput(Pointer dspConnection, PointerByReference output);

    int FMOD_DSPConnection_SetMix(Pointer dspConnection, float volume);

    int FMOD_DSPConnection_GetMix(Pointer dspConnection, FloatByReference volume);

    int FMOD_DSPConnection_SetMixMatrix(Pointer dspConnection, Pointer matrix, int outChannels, int inChannels, int inChannelHop);

    int FMOD_DSPConnection_GetMixMatrix(Pointer dspConnection, Pointer matrix, IntByReference outChannels, IntByReference inChannels, int inChannelHop);

    int FMOD_DSPConnection_GetType(Pointer dspConnection, IntByReference type);

    int FMOD_DSPConnection_SetUserData(Pointer dspConnection, Pointer userData);

    int FMOD_DSPConnection_GetUserData(Pointer dspConnection, PointerByReference userData);

    int FMOD_Geometry_Release(Pointer geometry);

    int FMOD_Geometry_AddPolygon(Pointer geometry, float directOcclusion, float reverbOcclusion, int doublesidedBool, int numVertices, Pointer vertices, IntByReference polygonIndex);

    int FMOD_Geometry_GetNumPolygons(Pointer geometry, IntByReference numPolygons);

    int FMOD_Geometry_GetMaxPolygons(Pointer geometry, IntByReference maxPolygons, IntByReference maxVertices);

    int FMOD_Geometry_GetPolygonNumVertices(Pointer geometry, int index, IntByReference numVertices);

    int FMOD_Geometry_SetPolygonVertex(Pointer geometry, int index, int vertexIndex, Pointer vertex);

    int FMOD_Geometry_GetPolygonVertex(Pointer geometry, int index, int vertexIndex, Pointer vertex);

    int FMOD_Geometry_SetPolygonAttributes(Pointer geometry, int index, float directOcclusion, float reverbOcclusion, int doublesidedBool);

    int FMOD_Geometry_GetPolygonAttributes(Pointer geometry, int index, FloatByReference directOcclusion, FloatByReference reverbOcclusion, IntByReference doublesided);

    int FMOD_Geometry_SetActive(Pointer geometry, int activeBool);

    int FMOD_Geometry_GetActive(Pointer geometry, IntByReference activeBool);

    int FMOD_Geometry_SetRotation(Pointer geometry, Pointer forward, Pointer up);

    int FMOD_Geometry_GetRotation(Pointer geometry, Pointer forward, Pointer up);

    int FMOD_Geometry_SetPosition(Pointer geometry, Pointer position);

    int FMOD_Geometry_GetPosition(Pointer geometry, Pointer position);

    int FMOD_Geometry_SetScale(Pointer geometry, Pointer scale);

    int FMOD_Geometry_GetScale(Pointer geometry, Pointer scale);

    int FMOD_Geometry_Save(Pointer geometry, Pointer data, IntByReference dataSize);

    int FMOD_Geometry_SetUserData(Pointer geometry, Pointer userData);

    int FMOD_Geometry_GetUserData(Pointer geometry, PointerByReference userData);

    int FMOD_Reverb3D_Release(Pointer reverb3d);

    int FMOD_Reverb3D_Set3DAttributes(Pointer reverb3d, Pointer position, float minDistance, float maxDistance);

    int FMOD_Reverb3D_Get3DAttributes(Pointer reverb3d, Pointer position, FloatByReference minDistance, FloatByReference maxDistance);

    int FMOD_Reverb3D_SetProperties(Pointer reverb3d, Pointer properties);

    int FMOD_Reverb3D_GetProperties(Pointer reverb3d, Pointer properties);

    int FMOD_Reverb3D_SetActive(Pointer reverb3d, int activeBool);

    int FMOD_Reverb3D_GetActive(Pointer reverb3d, IntByReference activeBool);

    int FMOD_Reverb3D_SetUserData(Pointer reverb3d, Pointer userData);

    int FMOD_Reverb3D_GetUserData(Pointer reverb3d, PointerByReference userData);

    interface FMODMemoryAllocCallbackFFI extends Callback { // Don't care
        Pointer invoke(int size, int type, String source);
    }

    interface FMODMemoryReallocCallbackFFI extends Callback { // Don't care
        Pointer invoke(Pointer ptr, int size, int type, String source);
    }

    interface FMODMemoryFreeCallbackFFI extends Callback { // Don't care
        void invoke(Pointer ptr, int type, String source);
    }

    interface FMODDebugCallbackFFI extends Callback { // Don't care
        void invoke(int level, String file, int line, String message);
    }

    interface FMODFileOpenCallbackFFI extends Callback { // Don't care
        int invoke(String name, int unicode, PointerByReference file);
    }

    interface FMODFileCloseCallbackFFI extends Callback { // Don't care
        int invoke(Pointer file);
    }

    interface FMODFileReadCallbackFFI extends Callback { // Don't care
        int invoke(Pointer file, Pointer buffer, int sizeBytes, IntByReference bytesRead);
    }

    interface FMODFileSeekCallbackFFI extends Callback { // Don't care
        int invoke(Pointer file, int offset, int whence);
    }

    interface FMODFileAsyncReadCallbackFFI extends Callback { // Don't care
        int invoke(Pointer file, Pointer buffer, int sizeBytes, IntByReference bytesRead, Pointer callbackData);
    }

    interface FMODFileAsyncCancelCallbackFFI extends Callback { // Don't care
        int invoke(Pointer file, Pointer callbackData);
    }

    interface FMODSystemCallbackFFI extends Callback { // Don't care
        int invoke(Pointer system, int type, Pointer commandData1, Pointer commandData2, Pointer userData);
    }

    interface FMOD3DRolloffCallbackFFI extends Callback { // Don't care
        float invoke(Pointer channel, float distance);
    }

    interface FMODChannelCallbackFFI extends Callback {
        int invoke(Pointer channelControl, int controlType, int callbackType, Pointer commandData1, Pointer commandData2);
    }

    interface FMODChannelGroupCallbackFFI extends Callback {
        int invoke(Pointer channelControl, int controlType, int callbackType, Pointer commandData1, Pointer commandData2);
    }

    interface FMODDSPCallbackFFI extends Callback { // Don't care
        int invoke(Pointer dsp, int type, Pointer commandData);
    }
}