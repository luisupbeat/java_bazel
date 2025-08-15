# src/test/java/com/ejemplo/app2/BUILD

load("@rules_java//java:defs.bzl", "java_test")

java_test(
    name = "app_test",
    srcs = glob(["*Test.java"]),
    test_class = "com.ejemplo.app2.MainTest",
    deps = [
        "//src/main/java/com/ejemplo/app2:app_lib",
        "@maven//:junit_junit",
    ],
)