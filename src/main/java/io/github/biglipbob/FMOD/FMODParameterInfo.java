package io.github.biglipbob.FMOD;

import com.sun.jna.*;
import java.util.*;

import static io.github.biglipbob.FMOD.FMODConstants.*;

public class FMODParameterInfo extends Structure {
    public int type; // FMOD_DSP_PARAMETER_TYPE
    public byte[] name = new byte[16];
    public byte[] label = new byte[16];
    public String description;
    public ParamUnion union;

    FMODParameterInfo() {
        super();
    }

    FMODParameterInfo(Pointer p) {
        super(p);
        read();

        // Handle all initializations here to ensure that post-fact the user cannot invoke JNA APIs and cause breakage
        union.setType(switch (type) {
            case FMOD_DSP_PARAMETER_TYPE_FLOAT -> FloatDesc.class;
            case FMOD_DSP_PARAMETER_TYPE_INT -> IntDesc.class;
            case FMOD_DSP_PARAMETER_TYPE_BOOL -> BoolDesc.class;
            case FMOD_DSP_PARAMETER_TYPE_DATA -> DataDesc.class;
            default -> throw new IllegalStateException("FMODParameterInfo: Unknown parameter type " + type);
        });

        union.read();
    }

    @Override protected List<String> getFieldOrder() {
        return Arrays.asList("type", "name", "label", "description", "union");
    }

    public String getName() {
        return Native.toString(name);
    }

    public int getType() {
        return type;
    }

    public String getLabel() {
        return Native.toString(label);
    }

    public String getDescription() {
        return description;
    }

    public GenericDesc getParameterDesc() {
        return switch (type) {
            case FMOD_DSP_PARAMETER_TYPE_FLOAT -> union.floatdesc;
            case FMOD_DSP_PARAMETER_TYPE_INT -> union.intdesc;
            case FMOD_DSP_PARAMETER_TYPE_BOOL -> union.booldesc;
            case FMOD_DSP_PARAMETER_TYPE_DATA -> union.datadesc;
            default -> throw new IllegalStateException("FMODParameterInfo: Unknown parameter type " + type);
        };
    }

    public static class ParamUnion extends Union {
        public FloatDesc floatdesc;
        public IntDesc intdesc;
        public BoolDesc booldesc;
        public DataDesc datadesc;

        public ParamUnion() {
        }
    }

    public static abstract class GenericDesc extends Structure {
        public GenericDesc() {
        }
    }

    public static class FloatDesc extends GenericDesc {
        public float min;
        public float max;
        public float defaultVal;

        public FloatDesc() {
        }

        @Override protected List<String> getFieldOrder() {
            return Arrays.asList("min", "max", "defaultVal");
        }
    }

    public static class IntDesc extends GenericDesc {
        public int min;
        public int max;
        public int defaultVal;

        public IntDesc() {
        }

        @Override protected List<String> getFieldOrder() {
            return Arrays.asList("min", "max", "defaultVal");
        }
    }

    public static class BoolDesc extends GenericDesc {
        public byte defaultVal;

        public BoolDesc() {
        }

        @Override protected List<String> getFieldOrder() {
            return Collections.singletonList("defaultVal");
        }
    }

    public static class DataDesc extends GenericDesc {
        public int len;
        public Pointer data;

        public DataDesc() {
        }

        @Override protected List<String> getFieldOrder() {
            return Arrays.asList("len", "data");
        }
    }
}
