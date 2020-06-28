# 这个文件相当于Gradle的buidle.gradle,是CMake的构建脚本,用来构建原生库
# Sets the minimum version of CMake required to build the native
# library. You should either keep the default value or only pass a
# value of 3.4.0 or lower.

cmake_minimum_required(VERSION 3.4.1)

# 1.设置生成的so动态库最后输出的路径,★★★gradle3.0以上版本不要写这句,否则报错★★★
# set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/${PROJECT}/libs/${ANDROID_ABI})

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds it for you.
# Gradle automatically packages shared libraries with your APK.

# 如果想打包第二个.so文件,把这个add_library再写一个
add_library( # Sets the name of the library.
             native-lib     # c/cpp代码将要编译成为so库的名称，java代码加载库文件要用这个名称

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             # Associated headers in the same location as their source
             # file are automatically included.
             # 2.新增的.c文件都写在下面
             src/main/cpp/Log.c
             src/main/cpp/native-lib.c
             src/main/cpp/global.cpp
             src/main/cpp/c-call-java.c )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because system libraries are included in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

# 日志库
find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in the
# build script, prebuilt third-party libraries, or system libraries.

# 如果想打包第二个.so文件,把这个target_link_libraries再写一个
target_link_libraries( # Specifies the target library.
                       native-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
