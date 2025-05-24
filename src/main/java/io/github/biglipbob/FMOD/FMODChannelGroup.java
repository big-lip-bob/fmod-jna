package io.github.biglipbob.FMOD;

import java.lang.ref.Cleaner;
import com.sun.jna.Pointer;

import static io.github.biglipbob.FMOD.FMODConstants.FMOD_OK;
import static io.github.biglipbob.FMOD.FMOD.*;

public class FMODChannelGroup extends FMODChannelGroupRef implements AutoCloseable {
    final Cleaner.Cleanable cleanable;

    FMODChannelGroup(Pointer ptr) {
        super(ptr);
        cleanable = CLEANER.register(this, () -> release(ptr));
    }

    private static void release(Pointer ptr) {
        int result = CALL.FMOD_ChannelGroup_Release(ptr);
        if (result != FMOD_OK) throw new FMODException("FMOD_ChannelGroup_Release", result);
    }

    /// Releases the memory for the object.
    @Override public void close() {
        cleanable.clean();
    }
}
