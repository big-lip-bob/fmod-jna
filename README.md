# FMOD-JNA

Cross-platform Java wrapper for the FMOD audio engine using JNA.

[![Maven Central](https://img.shields.io/maven-central/v/io.github.biglipbob/fmod-jna.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.github.biglipbob/fmod-jna)
[![License](https://img.shields.io/github/license/big-lip-bob/fmod-jna.svg?label=License)](LICENSE)
[![Java Version](https://img.shields.io/badge/Java-24-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk24-archive-downloads.html)
[![JNA Version](https://img.shields.io/maven-central/v/net.java.dev.jna/jna.svg?label=JNA)](https://mvnrepository.com/artifact/net.java.dev.jna/jna)

## Overview

The goal of this project is to be an idiomatic, high-performance Java wrapper for the FMOD audio engine,
leveraging JNA to provide a seamless interface to the FMOD Core API.

## Features

### Fully Implemented:
- Audio playback and streaming
- Real-time DSP effects (Chorus, Compressor, EQ, FFT, etc.)
- Channel and ChannelGroup management

### Partially Implemented:
- 3D Audio (structure-wise, not code wise)
- Plugin system (structure-wise, not code wise)
- Memory management (cases which make NPEs or cause potential VM crashes can easily be made)

### Not Yet Implemented:
- Recording functionality
- Geometry-based occlusion
- Advanced 3D positioning

## Installation

### Maven

```xml
<dependency>
    <groupId>io.github.biglipbob</groupId>
    <artifactId>fmod-jna</artifactId>
    <version>0.0.1</version>
</dependency>
```

## ⚠️ Important: FMOD Native Libraries Required

**This package does NOT include the FMOD native libraries.** You must obtain them separately
from [FMOD.com](https://www.fmod.com/) and place them in the correct directory structure.

### Directory Structure

The FMOD native libraries must be placed in the following directory structure relative to your application's working
directory:

```
your-project/
├── fmod/
│   ├── win-amd64/          # Windows 64-bit
│   │   ├── fmod.dll
│   │   └── vcruntime140_app.dll (if required)
│   ├── win-x86/            # Windows 32-bit
│   │   └── fmod.dll
│   ├── lin-amd64/          # Linux 64-bit
│   │   └── libfmod.so
│   ├── mac-aarch64/        # macOS Apple Silicon
│   │   └── libfmod.dylib
│   └── mac-x86_64/         # macOS Intel
│       └── libfmod.dylib
```

### Obtaining FMOD Libraries

1. Visit [FMOD.com](https://www.fmod.com/) and create an account
2. Download the FMOD Core API for your target platforms
3. Extract the appropriate library files:
    - **Windows**: `fmod.dll` (and `vcruntime140_app.dll` if needed)
    - **Linux**: `libfmod.so`
    - **macOS**: `libfmod.dylib`
4. Place them in the corresponding platform directories as shown above

### Licensing

Please ensure you comply with [FMOD's licensing terms](https://www.fmod.com/licensing). FMOD is free for non-commercial
use, but commercial applications require a license.

## Quick Start

```java
import io.github.biglipbob.FMOD.*;

public class BasicExample {
    public static void main(String[] args) {
        // Create FMOD system
        try (FMODSystem system = FMOD.createSystem(32)) {

            // Load a sound
            FMODSound sound = system.createSound("path/to/your/audio.wav");

            // Play the sound
            FMODChannel channel = system.playSound(sound, null, false);

            // Main loop
            while (channel.isPlaying()) {
                system.update();
                Thread.sleep(10);
            }

            // Cleanup (automatic with try-with-resources)
        }
    }
}
```

## Error Handling

```java
try {
    FMODSound sound = system.createSound("nonexistent.wav");
} catch (FMODException e) {
    System.err.println("FMOD Error: " + e.getMessage());
    System.err.println("Error Code: " + e.getCode());
}
```

## Requirements

- **Java**: 24
- **JNA**: 5.16.0 (included as a dependency)
- **FMOD Core API**: 2.03.06 or compatible

## Platform Support

- Windows (x86, x64)
- Linux (x64)
- macOS (Intel x64, Apple Silicon)

## License

This wrapper is released under the MIT License. However, please note that FMOD itself has its own licensing terms which
you must comply with separately.

## Support

- **Issues**: [GitHub Issues](https://github.com/big-lip-bob/fmod-jna/issues)
- **FMOD Documentation**: [FMOD Docs](https://www.fmod.com/docs/)
- **FMOD Support**: [FMOD Support](https://www.fmod.com/support/)

## Acknowledgments

- Firelight Technologies for creating the excellent FMOD audio engine
- The JNA project for making native library access possible in Java