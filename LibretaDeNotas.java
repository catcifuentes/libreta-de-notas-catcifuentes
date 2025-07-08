import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LibretaDeNotas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, ArrayList<Double>> calificaciones = new HashMap<>();
        final double NOTA_MAXIMA = 7.0;
        final double NOTA_APROBACION = 4.0;

        // Ingresar cantidad de alumnos
        int numAlumnos;
        do {
            System.out.print("Ingrese cantidad de alumnos: ");
            numAlumnos = scanner.nextInt();
            if(numAlumnos <= 0) {
                System.out.println("Debe ingresar un número positivo");
            }
        } while(numAlumnos <= 0);

        // Ingresar cantidad de notas por alumno
        int numNotas;
        do {
            System.out.print("Ingrese cantidad de notas por alumno: ");
            numNotas = scanner.nextInt();
            if(numNotas <= 0) {
                System.out.println("Debe ingresar un número positivo");
            }
        } while(numNotas <= 0);
        scanner.nextLine(); // Limpiar buffer

        // Ingresar datos de alumnos y notas (validando rango 1-7)
        for (int i = 0; i < numAlumnos; i++) {
            System.out.print("\nNombre del alumno " + (i+1) + ": ");
            String nombre = scanner.nextLine();

            ArrayList<Double> notas = new ArrayList<>();
            for (int j = 0; j < numNotas; j++) {
                double nota;
                do {
                    System.out.print("Nota " + (j+1) + " (1-7): ");
                    nota = scanner.nextDouble();
                    scanner.nextLine(); // Limpiar buffer
                    if(nota < 1 || nota > NOTA_MAXIMA) {
                        System.out.println("La nota debe estar entre 1 y 7");
                    }
                } while(nota < 1 || nota > NOTA_MAXIMA);
                notas.add(nota);
            }
            calificaciones.put(nombre, notas);
        }

        // Calcular promedios
        HashMap<String, Double> promedios = calcularPromedios(calificaciones);

        // Menú principal
        int opcion;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Mostrar promedios de todos los alumnos");
            System.out.println("2. Verificar si un alumno aprobó");
            System.out.println("3. Comparar alumno con promedio del curso");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    mostrarPromedios(promedios, NOTA_APROBACION);
                    break;
                case 2:
                    verificarAprobacion(scanner, promedios, NOTA_APROBACION);
                    break;
                case 3:
                    compararConPromedioCurso(scanner, promedios);
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 0);

        scanner.close();
    }

    public static HashMap<String, Double> calcularPromedios(HashMap<String, ArrayList<Double>> calificaciones) {
        HashMap<String, Double> promedios = new HashMap<>();
        for (String nombre : calificaciones.keySet()) {
            ArrayList<Double> notas = calificaciones.get(nombre);
            double suma = 0;
            for (double nota : notas) {
                suma += nota;
            }
            promedios.put(nombre, suma / notas.size());
        }
        return promedios;
    }

    public static void mostrarPromedios(HashMap<String, Double> promedios, double notaAprobacion) {
        System.out.println("\n--- PROMEDIOS DE ALUMNOS ---");
        System.out.println("(Aprobación: nota ≥ " + notaAprobacion + ")");
        for (String nombre : promedios.keySet()) {
            double promedio = promedios.get(nombre);
            String estado = (promedio >= notaAprobacion) ? "APROBADO" : "REPROBADO";
            System.out.printf("%s: %.1f - %s\n", nombre, promedio, estado);
        }
    }

    public static void verificarAprobacion(Scanner scanner, HashMap<String, Double> promedios, double notaAprobacion) {
        System.out.print("\nIngrese nombre del alumno: ");
        String nombre = scanner.nextLine();

        if (promedios.containsKey(nombre)) {
            double promedio = promedios.get(nombre);
            if (promedio >= notaAprobacion) {
                System.out.printf("%s está APROBADO con %.1f (≥ %.1f)\n",
                        nombre, promedio, notaAprobacion);
            } else {
                System.out.printf("%s está REPROBADO con %.1f (< %.1f)\n",
                        nombre, promedio, notaAprobacion);
            }
        } else {
            System.out.println("Alumno no encontrado");
        }
    }

    public static void compararConPromedioCurso(Scanner scanner, HashMap<String, Double> promedios) {
        // Calcular promedio general del curso
        double sumaTotal = 0;
        for (double promedio : promedios.values()) {
            sumaTotal += promedio;
        }
        double promedioCurso = sumaTotal / promedios.size();

        System.out.printf("\nPromedio general del curso: %.1f\n", promedioCurso);
        System.out.print("Ingrese nombre del alumno: ");
        String nombre = scanner.nextLine();

        if (promedios.containsKey(nombre)) {
            double promedioAlumno = promedios.get(nombre);
            System.out.printf("Promedio de %s: %.1f\n", nombre, promedioAlumno);

            if (promedioAlumno > promedioCurso) {
                System.out.printf("Está SOBRE el promedio del curso (+%.1f)\n",
                        promedioAlumno - promedioCurso);
            } else if (promedioAlumno < promedioCurso) {
                System.out.printf("Está BAJO el promedio del curso (-%.1f)\n",
                        promedioCurso - promedioAlumno);
            } else {
                System.out.println("Está EN el promedio del curso");
            }
        } else {
            System.out.println("Alumno no encontrado");
        }
    }
}