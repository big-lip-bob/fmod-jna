package io.github.biglipbob.FMOD;

import com.sun.jna.*;
import java.util.*;

import static io.github.biglipbob.FMOD.FMODConstants.*;

public class FMODTagData extends Structure {

    public int tagType;
    public int dataType;
    public String name;
    public Pointer data;
    public int dataSize;
    public boolean updated;

    FMODTagData() {
        super();
    }

    FMODTagData(Pointer p) {
        super(p);
        read();
    }

    public int getTagType() {
        return tagType;
    }

    public String getName() {
        return name;
    }

    public int getDataType() {
        return dataType;
    }

    public int getDataSize() {
        return dataSize;
    }

    public boolean isUpdated() {
        return updated;
    }

    public long getIntValue() {
        if (dataType != FMOD_TAGDATATYPE_INT) throw new IllegalStateException("Tag data is not of type INT.");
        if (data == null) throw new IllegalStateException("Data pointer is null.");

        return switch (dataSize) {
            case 1 -> data.getByte(0);
            case 2 -> data.getShort(0);
            case 4 -> data.getInt(0);
            case 8 -> data.getLong(0);
            default -> throw new IllegalStateException("Unexpected integer size: " + dataSize + " bytes.");
        };
    }

    public double getFloatValue() {
        if (dataType != FMOD_TAGDATATYPE_FLOAT) throw new IllegalStateException("Tag data is not of type FLOAT.");
        if (data == null) throw new IllegalStateException("Data pointer is null.");

        return switch (dataSize) {
            case 4 -> data.getFloat(0);
            case 8 -> data.getDouble(0);
            default -> throw new IllegalStateException("Unexpected float size: " + dataSize + " bytes.");
        };
    }

    public String getStringValue() {
        if (data == null) throw new IllegalStateException("Data pointer is null.");

        return data.getString(0, switch (dataType) {
            case FMOD_TAGDATATYPE_STRING -> "US-ASCII";
            case FMOD_TAGDATATYPE_STRING_UTF8 -> "UTF-8";
            case FMOD_TAGDATATYPE_STRING_UTF16 -> "UTF-16LE";
            case FMOD_TAGDATATYPE_STRING_UTF16BE -> "UTF-16BE";
            default -> throw new IllegalStateException("Tag data is not of type STRING.");
        });
    }

    public byte[] getBinaryData() {
        if (dataType != FMOD_TAGDATATYPE_BINARY) throw new IllegalStateException("Tag data is not of type BINARY.");
        if (data == null) throw new IllegalStateException("Data pointer is null.");

        return data.getByteArray(0, dataSize);
    }

    public <T extends Structure> T getStructure(Class<T> clazz) {
        if (dataType != FMOD_TAGDATATYPE_BINARY) throw new IllegalStateException("Tag data is not of type BINARY.");
        if (data == null) throw new IllegalStateException("Data pointer is null.");

        T instance = Structure.newInstance(clazz, data);
        if (instance.size() != dataSize)
            throw new IllegalStateException(clazz.getSimpleName() + " does not match size of " + dataSize + '.');
        instance.read();
        return instance;
    }

    @Override protected List<String> getFieldOrder() {
        return Arrays.asList("tagType", "dataType", "name", "data", "dataSize", "updated");
    }
}