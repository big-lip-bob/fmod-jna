package io.github.biglipbob.FMOD;

import com.sun.jna.Pointer;

public class FMODSyncPoint {
    final Pointer ptr;

    FMODSyncPoint(Pointer ptr) {
        this.ptr = ptr;
    }
}
