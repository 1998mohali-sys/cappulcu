cmake_minimum_required(VERSION 3.22.1)
project("cappulcu-native")

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -O3")

# Llama JNI kütüphanesi
add_library(
    llama-android
    SHARED
    llama-android.cpp
)

find_library(log-lib log)
target_link_libraries(llama-android ${log-lib})

# Piper JNI kütüphanesi
add_library(
    piper-android
    SHARED
    piper-android.cpp
)

target_link_libraries(piper-android ${log-lib})

# Whisper JNI kütüphanesi
add_library(
    whisper-android
    SHARED
    whisper-android.cpp
)

target_link_libraries(whisper-android ${log-lib})
