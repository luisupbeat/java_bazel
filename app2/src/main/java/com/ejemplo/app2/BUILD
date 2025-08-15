java_library(
    name = "app_lib",
    srcs = ["Area.java"],
    visibility = ["//visibility:public"],
)

java_binary(
    name = "app",
    srcs = ["Main.java"],
    deps = [":app_lib"],
    main_class = "com.ejemplo.app2.Main",
)