package io.github.biglipbob.FMOD;

import java.lang.ref.Cleaner;
import java.io.File;
import java.util.*;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;

import static io.github.biglipbob.FMOD.FMODConstants.*;

/**
 * The {@code FMOD} class provides a Java Native Access interface to the FMOD audio engine native library.
 * <p>
 * For more details, refer to the FMOD documentation:
 * <ul>
 *     <li><a href="https://www.fmod.com/docs/2.03/api/core-guide.html">FMOD Core Guide</a></li>
 *     <li><a href="https://www.fmod.com/docs/2.03/api/core-api.html">FMOD Core API</a></li>
 * </ul>
 * FMOD is a product of Firelight Technologies Proprietary Limited
 */
public class FMOD {

    static final Cleaner CLEANER = Cleaner.create();
    static final Map<Pointer, Object> USERDATA = new HashMap<>();
    private static final String SEARCH_PATH = "fmod";
    static FMODFFI CALL = null;

    private FMOD() {
    }

    /// To be used to initialize in advance to be able to report errors, or lazily inside FMOD's System Create (everything sprawls from it) (or Debug calls)
    public static void initializeFMOD() {
        if (CALL != null) return;
        String libPath = System.getProperty("user.dir") + File.separator + SEARCH_PATH + File.separator + System.getProperty("os.name").substring(0, 3).toLowerCase() + '-' + System.getProperty("os.arch");

        String basePath = System.getProperty("java.library.path");
        System.setProperty("java.library.path", basePath != null ? basePath + File.pathSeparator + libPath : libPath); // Windows requires vcruntime140_app.dll as FMOD is dynamically linked against

        NativeLibrary.addSearchPath("fmod", libPath);
        CALL = Native.load("fmod", FMODFFI.class, Collections.singletonMap(Library.OPTION_STRING_ENCODING, "UTF-8")); // Yet another fix for Windows, as FMOD exclusively uses UTF-8 which is good
    }

    static Memory createMixMatrixMemory(float[][] matrix) {
        int out = matrix.length, in = 0 < out ? matrix[0].length : 0;
        if (!(0 < in)) throw new IllegalArgumentException("Empty matrix");

        Memory mem = new Memory(4L * out * in);
        for (int row = 0; row < out; row++) {
            if (matrix[row].length != in) throw new IllegalStateException("Irregular matrix shape");
            for (int col = 0; col < in; col++) {
                float value = matrix[row][col];
                int offset = (row * in + col) * 4;
                mem.setFloat(offset, value);
            }
        }
        return mem;
    }

    static float[][] createMixMatrixFrom(Memory buffer, int out, int in) {
        float[][] matrix = new float[out][in];
        for (int row = 0; row < out; row++) matrix[row] = buffer.getFloatArray((long) row * in, in);
        return matrix;
    }

    /// Creates an instance of the FMOD system.
    public static FMODSystem createSystem(int maxChannels, int flags) { // Because code before this/super() is experimental
        initializeFMOD();

        PointerByReference systemRef = new PointerByReference();
        int result;
        synchronized (FMOD.class) {
            result = CALL.FMOD_System_Create(systemRef, FMODConstants.FMOD_VERSION);
        }
        if (result != FMODConstants.FMOD_OK)
            throw new FMODException("FMOD_System_Create", result);
        Pointer ptr = systemRef.getValue();

        result = CALL.FMOD_System_Init(ptr, maxChannels, flags, null);
        if (result != FMODConstants.FMOD_OK)
            throw new FMODException("FMOD_System_Init", result);

        return new FMODSystem(ptr);
    }

    /// Creates an instance of the FMOD system.
    public static FMODSystem createSystem(int maxChannels, int flags, int sampleRate, int bufferSize, int bufferCount) { // Because code before this/super() is experimental
        initializeFMOD();

        PointerByReference systemRef = new PointerByReference();
        int result;
        synchronized (FMOD.class) {
            result = CALL.FMOD_System_Create(systemRef, FMODConstants.FMOD_VERSION);
        }
        if (result != FMODConstants.FMOD_OK)
            throw new FMODException("FMOD_System_Create", result);
        Pointer ptr = systemRef.getValue();

        FMODSystem system = new FMODSystem(ptr);

        system.setSampleRate(sampleRate);
        system.setDSPBufferSize(bufferSize, bufferCount);

        result = CALL.FMOD_System_Init(ptr, maxChannels, flags, null);
        if (result != FMODConstants.FMOD_OK) {
            system.close(); // Just to make sure
            throw new FMODException("FMOD_System_Init", result);
        }

        return system;
    }

    /// Creates an instance with no flags
    public static FMODSystem createSystem(int maxChannels) {
        return createSystem(maxChannels, 0);
    }

    /// Allows the use of the FMOD Studio Profiler
    public static FMODSystem createSystemWithProfiler(int maxChannels) {
        return createSystem(maxChannels, FMOD_INIT_PROFILE_ENABLE);
    }

    /// Initializes debugging on console.
    public static void debugConsole() {
        initializeFMOD();

        int result = CALL.FMOD_Debug_Initialize(FMOD_DEBUG_LEVEL_ERROR | FMOD_DEBUG_LEVEL_WARNING | FMOD_DEBUG_LEVEL_LOG, FMOD_DEBUG_MODE_TTY, null, null);
        if (result != FMODConstants.FMOD_OK) throw new FMODException("FMOD_Debug_Initialize", result);
    }

    /// Initializes debugging with a file output.
    public static void debugFile(String filename) {
        initializeFMOD();

        int result = CALL.FMOD_Debug_Initialize(FMOD_DEBUG_LEVEL_ERROR | FMOD_DEBUG_LEVEL_WARNING | FMOD_DEBUG_LEVEL_LOG, FMOD_DEBUG_MODE_FILE, null, filename);
        if (result != FMODConstants.FMOD_OK) throw new FMODException("FMOD_Debug_Initialize", result);
    }
}
