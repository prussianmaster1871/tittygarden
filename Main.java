import java.io.*;
import java.util.ArrayList;

class ThreadContadora extends Thread {
    // ARMAZENAR AQUI DE ALGUMA FORMA, EX: Array(Caracter -> Ocorrencia)
    ArrayList<File> files;

    public ThreadContadora() {
        this.files = new ArrayList<>();
    }

    @Override
    public void run() {
        for (int i = 0; i < files.size(); i++) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(files.get(i)));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    for (int j = 0; j < line.length(); j++) {
                        char c = line.charAt(j);

                        // CONTAR CADA OCORRENCIA DE CARACTER

                        System.out.println(c);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addFile(File file) {
        files.add(file);
    }

    // GET OCORRENCIAS DE UMA THREAD
}

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        File[] files = new File("amostra/").listFiles();

        int numThreads = Runtime.getRuntime().availableProcessors();
        ArrayList<ThreadContadora> threads = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            threads.add(new ThreadContadora());
        }

        for (int i = 0; i < files.length; i++) {
            // Selecionar qual thread vai ler o arquivo
            ThreadContadora thread = threads.get(i % numThreads);
            thread.addFile(files[i]);
        }

        for (ThreadContadora thread : threads) {
            thread.start();
        }

        for (ThreadContadora thread : threads) {
            thread.join();
        }

        // contador final de ocorrencias

        for (ThreadContadora thread : threads) {
            // thread.getOcorrencias()

            // adiciona no contador final
        }

//        System.out.println(ocorrencias por caracter);

        System.out.println("Numero de threads: " + numThreads);
    }
}