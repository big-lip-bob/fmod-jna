package io.github.biglipbob.FMOD;

import com.sun.jna.*;
import java.util.*;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class FMODCreateSoundExInfo extends Structure {
    public int cbsize;
    public int length;
    public int fileoffset;
    public int numchannels;
    public int defaultfrequency;
    public int format;
    public int decodebuffersize;
    public int initialsubsound;
    public int numsubsounds;
    public Pointer inclusionlist;
    public int inclusionlistnum;
    public Pointer pcmreadcallback;
    public Pointer pcmsetposcallback;
    public Pointer nonblockcallback;
    public String dlsname;
    public String encryptionkey;
    public int maxpolyphony;
    public Pointer userdata;
    public int suggestedsoundtype;
    public Pointer fileuseropen;
    public Pointer fileuserclose;
    public Pointer fileuserread;
    public Pointer fileuserseek;
    public Pointer fileuserasyncread;
    public Pointer fileuserasynccancel;
    public Pointer fileuserdata;
    public int filebuffersize;
    public int channelorder;
    public Pointer initialsoundgroup;
    public int initialseekposition;
    public int initialseekpostype;
    public int ignoresetfilesystem;
    public int audioqueuepolicy;
    public int minmidigranularity;
    public int nonblockthreadid;
    public Pointer fsbguid;

    public FMODCreateSoundExInfo() {
        super();
        cbsize = size();
    }

    @Override protected List<String> getFieldOrder() {
        return Arrays.asList(
                "cbsize", "length", "fileoffset", "numchannels", "defaultfrequency", "format", "decodebuffersize",
                "initialsubsound", "numsubsounds", "inclusionlist", "inclusionlistnum", "pcmreadcallback",
                "pcmsetposcallback", "nonblockcallback", "dlsname", "encryptionkey", "maxpolyphony", "userdata",
                "suggestedsoundtype", "fileuseropen", "fileuserclose", "fileuserread", "fileuserseek",
                "fileuserasyncread", "fileuserasynccancel", "fileuserdata", "filebuffersize", "channelorder",
                "initialsoundgroup", "initialseekposition", "initialseekpostype", "ignoresetfilesystem",
                "audioqueuepolicy", "minmidigranularity", "nonblockthreadid", "fsbguid"
        );
    }
}
