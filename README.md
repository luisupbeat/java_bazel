# Tutorial: Configuración de Proyecto Java con Bazel

## Introducción

Bazel es un sistema de construcción de código abierto desarrollado por Google que permite compilar y probar código de manera rápida y confiable. Es especialmente útil para proyectos grandes y monorepos.

## Requisitos Previos

- **Java JDK 8 o superior** instalado
- **Bazel** instalado (versión 4.0 o superior recomendada)
    - Documentación oficial: https://bazel.build/install/windows?hl=es-419     
- Editor de texto o IDE de tu preferencia

### Verificar Instalaciones

```bash
# Verificar Java
java -version
javac -version

# Verificar Bazel
bazel version
```

**Nota:** Si no tienes Bazel instalado, puedes descargarlo desde [bazel.build](https://bazel.build/install)

## Paso 1: Crear la Estructura del Proyecto

```bash
# Crear directorio raíz del proyecto
mkdir mi-proyecto-java-bazel
cd mi-proyecto-java-bazel

# Crear estructura de directorios
mkdir -p src/main/java/com/ejemplo/app
mkdir -p src/test/java/com/ejemplo/app
```

**Nota:** Esta estructura sigue las convenciones de Maven, pero Bazel es flexible con la organización de archivos.

## Paso 2: Configurar el Archivo WORKSPACE

Crea el archivo `WORKSPACE` en la raíz del proyecto:

```python
# WORKSPACE
workspace(name = "mi_proyecto_java")

# Cargar reglas de Java
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# Configurar reglas de Java (opcional, ya incluidas en Bazel)
# Si necesitas versiones específicas, puedes definirlas aquí
```

**Notas importantes:**
- El archivo `WORKSPACE` define el nombre del workspace y las dependencias externas
- Debe estar en la raíz del proyecto
- Las reglas básicas de Java ya están incluidas en Bazel

## Paso 3: Crear Archivos BUILD

### BUILD principal (raíz del proyecto)

Crea `BUILD` en la raíz:

```python
# BUILD
# Archivo BUILD raíz - puede estar vacío o contener reglas globales
```

### BUILD para el código fuente

Crea `src/main/java/com/ejemplo/app/BUILD`:

```python
# src/main/java/com/ejemplo/app/BUILD

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
    main_class = "com.ejemplo.app.Main",
    runtime_deps = [":app_lib"],
)
```

**Notas sobre las reglas:**
- `java_library`: Compila archivos Java en un JAR
- `java_binary`: Crea un ejecutable Java
- `glob(["*.java"])`: Incluye todos los archivos .java del directorio
- `visibility`: Controla qué otros targets pueden usar esta regla

### BUILD para tests

Crea `src/test/java/com/ejemplo/app/BUILD`:

```python
# src/test/java/com/ejemplo/app/BUILD

load("@rules_java//java:defs.bzl", "java_test")

java_test(
    name = "app_test",
    srcs = glob(["*Test.java"]),
    test_class = "com.ejemplo.app.MainTest",
    deps = [
        "//src/main/java/com/ejemplo/app:app_lib",
        "@maven//:junit_junit",
    ],
)
```

## Paso 4: Crear Código Java de Ejemplo

### Clase principal

Crea `src/main/java/com/ejemplo/app/Main.java`:

```java
package com.ejemplo.app;

public class Main {
    public static void main(String[] args) {
        System.out.println("¡Hola desde Bazel!");
        
        Calculadora calc = new Calculadora();
        int resultado = calc.sumar(5, 3);
        System.out.println("5 + 3 = " + resultado);
    }
}
```

### Clase de utilidad

Crea `src/main/java/com/ejemplo/app/Calculadora.java`:

```java
package com.ejemplo.app;

public class Calculadora {
    public int sumar(int a, int b) {
        return a + b;
    }
    
    public int restar(int a, int b) {
        return a - b;
    }
    
    public int multiplicar(int a, int b) {
        return a * b;
    }
}
```

## Paso 5: Crear Tests

Crea `src/test/java/com/ejemplo/app/MainTest.java`:

```java
package com.ejemplo.app;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {
    
    @Test
    public void testCalculadoraSumar() {
        Calculadora calc = new Calculadora();
        assertEquals(8, calc.sumar(5, 3));
        assertEquals(0, calc.sumar(-1, 1));
    }
    
    @Test
    public void testCalculadoraRestar() {
        Calculadora calc = new Calculadora();
        assertEquals(2, calc.restar(5, 3));
        assertEquals(-2, calc.restar(3, 5));
    }
}
```

## Paso 6: Configurar Dependencias Externas (Opcional)

Si necesitas dependencias externas como JUnit, crea un archivo `WORKSPACE` más completo:

```python
# WORKSPACE (versión extendida)
workspace(name = "mi_proyecto_java")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# Reglas de Maven para dependencias externas
RULES_JVM_EXTERNAL_TAG = "4.2"
RULES_JVM_EXTERNAL_SHA = "cd1a77b7b02e8e008439ca76fd34f5b07aecb8c752961f9640dea15e9e5ba1ca"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")
rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")
rules_jvm_external_setup()

# Dependencias Maven
load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "junit:junit:4.13.2",
        "org.hamcrest:hamcrest-core:1.3",
    ],
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)
```

**Nota:** Esta configuración permite usar dependencias de Maven Central.

## Paso 7: Comandos de Construcción y Ejecución

### Compilar el proyecto

```bash
# Compilar toda la aplicación
bazel build //src/main/java/com/ejemplo/app:all

# Compilar solo la biblioteca
bazel build //src/main/java/com/ejemplo/app:app_lib

# Compilar el ejecutable
bazel build //src/main/java/com/ejemplo/app:app
```

### Ejecutar la aplicación

```bash
# Ejecutar directamente
bazel run //src/main/java/com/ejemplo/app:app

# O ejecutar el binario compilado
./bazel-bin/src/main/java/com/ejemplo/app/app
```

### Ejecutar tests

```bash
# Ejecutar todos los tests
bazel test //src/test/java/com/ejemplo/app:all

# Ejecutar test específico
bazel test //src/test/java/com/ejemplo/app:app_test

# Ejecutar tests con salida detallada
bazel test //src/test/java/com/ejemplo/app:app_test --test_output=all
```

## Paso 8: Comandos Útiles de Bazel

```bash
# Ver todos los targets disponibles
bazel query //...

# Ver dependencias de un target
bazel query "deps(//src/main/java/com/ejemplo/app:app)"

# Limpiar archivos compilados
bazel clean

# Ver información del workspace
bazel info

# Análizar rendimiento de construcción
bazel build //... --profile=profile.json
```

## Estructura Final del Proyecto

```
mi-proyecto-java-bazel/
├── WORKSPACE
├── BUILD
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── ejemplo/
│   │               └── app/
│   │                   ├── BUILD
│   │                   ├── Main.java
│   │                   └── Calculadora.java
│   └── test/
│       └── java/
│           └── com/
│               └── ejemplo/
│                   └── app/
│                       ├── BUILD
│                       └── MainTest.java
└── bazel-* (directorios generados automáticamente)
```

## Consejos y Mejores Prácticas

### 1. Organización de Archivos BUILD
- Mantén los archivos BUILD cerca del código que describen
- Usa nombres descriptivos para los targets
- Agrupa reglas relacionadas en el mismo archivo BUILD

### 2. Gestión de Dependencias
- Especifica dependencias mínimas necesarias
- Usa `visibility` para controlar el acceso a los targets
- Prefiere dependencias específicas sobre wildcards

### 3. Optimización de Construcción
- Usa `java_library` para código reutilizable
- Evita dependencias circulares
- Considera usar `java_plugin` para procesadores de anotaciones

### 4. Testing
- Mantén los tests cerca del código fuente
- Usa nombres consistentes para los archivos de test
- Aprovecha la paralelización automática de Bazel

## Troubleshooting Común

### Error: "No such package"
- Verifica que exista un archivo BUILD en el directorio
- Revisa la sintaxis del path del target

### Error: "Target not found"
- Confirma que el nombre del target esté correctamente definido
- Usa `bazel query` para listar targets disponibles

### Error de compilación Java
- Verifica que la versión de Java sea compatible
- Revisa las dependencias en los archivos BUILD

### Rendimiento lento
- Usa `bazel clean` si hay problemas de cache
- Considera ajustar la configuración de memoria de Bazel

## Conclusión

Has configurado exitosamente un proyecto Java con Bazel. Este setup te proporciona:

- **Construcción rápida e incremental**
- **Paralelización automática**
- **Cache inteligente**
- **Reproducibilidad de builds**
- **Escalabilidad para proyectos grandes**

Para proyectos más complejos, puedes explorar características avanzadas como:
- Macros personalizadas
- Reglas customizadas
- Integración con IDEs
- Construcción remota
- Configuración multi-plataforma

¡Bazel te ayudará a manejar eficientemente el crecimiento de tu proyecto Java!****
