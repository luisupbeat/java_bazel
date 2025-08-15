# src/main/java/com/ejemplo/app1/BUILD

load("@rules_java//java:defs.bzl", "java_binary", "java_library")

# Biblioteca Java
java_library(
    name = "app_lib",
    srcs = glob(["*.java"]),
    visibility = ["//visibility:public"],
)

# Ejecutable Java
java_binary(
    name = "app",
    main_class = "com.ejemplo.app1.Main",
    runtime_deps = [":app_lib"],
)
