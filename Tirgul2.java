import java.util.Random;
import java.util.Scanner;

public class Tirgul2 {

    public static boolean biggerThanZero(int num) {
//        if (num > 0)
//            return true;
//        return false;
        return num > 0;
    }

    public static void calc(int num1, int num2, char operator) {
        int result = 0;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 == 0) {
                    System.out.println("Cannot divide by zero!");
                    return;
                }
                result = num1 / num2;
                break;
            default:
                System.out.println("Invalid operator!");
                return;
        }
        System.out.println("Result: " + result);
    }
    public static void guessGame(){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int num = random.nextInt(1000);
        int guess = -1;

        while (guess != num) {
            System.out.println("Guess a number between 0 to 1000");
            guess = scanner.nextInt();
            if (guess > num)
                System.out.println("Too big!");
            else if (guess < num)
                System.out.println("Too small!");
        }
        System.out.println("You guessed it!");
    }

    public static int[] createArray() {
        Random random = new Random();
        int size = random.nextInt(100);
        int[] arr = new int[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(i+1);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
//        for (int i = 0; i < arr.length; i++) {
//            System.out.print(arr[i] + " "   );
//        }
        for (int num : arr) {
            System.out.print(num + " "   );
        }
    }

    public static void main(String[] args) {
//        int num1 = 7;
//        int num2 = 5;
//        char operator = '/';
//        calc(num1, num2, operator);
//        guessGame();
        int[] arr = createArray();
        printArray(arr);
    }
}
