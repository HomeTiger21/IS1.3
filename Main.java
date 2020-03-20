package sample;
import java.util.Random;
import java.util.Scanner;

class Main {
    public static void main (String[] args) {

        Scanner console = new Scanner(System.in);
        System.out.print("Введите d: ");
        int d = console.nextInt();
        System.out.println();
        int[] gx = new int[d + 1];
        System.out.print("Введите g(x): ");
        for (int i = d; i >= 0; i--) {
            gx[i] = console.nextInt();

        }
        System.out.println();
        System.out.print("Input k: ");
        int k = console.nextInt();
        int[] message = new int[k];
        double sigma = 0.7; //вероятность ошибки в векторе ошибок
        double epsilon; //вероятность для определения количества эксперементов
        System.out.println("Введите вероятность ");
        epsilon = console.nextDouble();

        epsilon *= 100;
        int pe = (int)epsilon*4;
        int N = 900/pe;
        System.out.println(N);
        int Ne = 0; //счетчик числа колличества ошибок декодера
        Random r = new Random();
        double q; //для генерации 0 и 1
        int NeA = 0;
        //рандомно генерируем сообщение
        for (int w = 0; w < N; w++) {
            for (int i = 0; i < k; i++) {
                q = r.nextDouble();
                if (q > 0.5) {
                    message[i] = 1;
                } else {
                    message[i] = 0;
                }
            }
          //  System.out.println("сообщение  ");
          //  for (int i = k - 1; i >= 0; i--) {
          //      System.out.print(message[i] + "  ");
         //   }
            transfer test = new transfer(d, gx, k, message);
            int[] ax;
            ax = test.Coder(); //кодируем сообщение
         //   System.out.println("ax  ");
           // for (int i = k + d - 1; i >= 0; i--) {
          //     System.out.print(ax[i] + "  ");
          //  }
          //  System.out.println();
            int[] error = new int[d + k];
            boolean er = false; //флаг будет ли ошибка в векторе ошибок
                //рандомно сгенерированный вектор ошибок с вероятностью sigma
            for (int i = k + d - 1; i >= 0; i--) {
                q = r.nextDouble();
                if (q > sigma) {
                    error[i] = 1;
                    er = true;
                } else {
                    error[i] = 0;
                }
            }
            int[] res;
            res = test.Decoder(error, ax); //декодирование
          //  System.out.println("res  ");
          //  for (int i = k + d - 1; i >= 0; i--) {
         //       System.out.print(res[i] + "  ");
          //  }
          //  System.out.println();
            int E = test.Error(res); //вычисляем была ли ошибка в сообщении
            //если ошибку не обнаружили, но в векторе она была, то увеличиваем счетчик ошибок декодера
            if (E == 0 && er == true) {
                Ne++;
            }
            //алтернативное нахождение ошибки
            int E2 = test.ErrorAlternatives(res);
            if (E2 == 0 && er == true) {
                NeA++;
            }

        }
        System.out.println("число ошибок декодера " + Ne);
        //переводим все в double чтобы посчитать вероятность
        double ne = (double)Ne;
        double n = (double)N;
        double nea = (double)NeA ;
        System.out.println("Вероятность ошибки декодора" + ne/n);
        System.out.println("число ошибок декодора при альтернативном декодировании "+ NeA);
        System.out.println("Вероятность ошибки декодирования при альтернативном декодировании"+ nea/n);


    }
}
