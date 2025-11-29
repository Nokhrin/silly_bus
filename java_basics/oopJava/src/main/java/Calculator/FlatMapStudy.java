package Calculator;

import java.util.Optional;

/**
  План освоения такой
 
  Осваиваещь flatmap доя optional
 
  1. Фильтрация через flatmap
  2. Преобразование типа параметра через flatmap
  3. Цепочка вызовов flatmap
  
  Потом
 
  Комбинируешь прасеры вместе
  1. Последовательность
  2. Ветвления
  3. Повторы
  4. Рекурсия
  
  
  Задача - применить 
   - функциональный подход
   - Optional
   - flatMap
   - filter
   
  Практика - реализация калькулятора с поддержкой
   - игнорирования пробелов
   - обработки целых чисел
   - выполнения сложения и вычитания
   
   на вход поступает строка вида "пробел* цифра+ пробел* +|- пробел* цифра+ пробел*"
   примеры: "   2 -     1 ", "-5+6", "1 + 1", "-2   -  3   ", ...
   на выход передается Optional<Integer>, при ошибке - Optional.empty()
       
  концепты
   - Optional - контейнер для результата, который может быть пустым,
       Optional - чтобы избежать проверки на null при парсинге чисел и операторов
   - filter - фильтрация и валидация лексем
   
 ---
 
 Задача
   
 1
   
  Фильтрация в данном случае это преобразование
  Из значения Optional.of(-1) типа Optional<Integer>  
 
  В значение Optional.empty() типа Optional<Integer>
 
  Тип данных не меняется, 
    значение меняется, 
 при том меняется характерно для фильтрации: значение было и его не стало
  То есть отфильтровалось
 
 ---
  
 2
 
 Вторая важная операция преобразование типа
 Это смена типа Operation<Integer> в Operation<String>
 Меняеться параметр типа Integer в String

 Это две разные по сути операции выполняемые одним методом, покажи мне их четко
 Что ты понимаешь разницу, напиши пример
 
 ---
 
 3
 цепь вызовов
 
 
 ---
 
 4
 цепь вызовов 2
 
 вторая форма flatMap   
 Optional<Integer> aOpt = ...
 Optional<Integer> bOpt = ...
 Optional<Integer> cOpt =
    aOpt.flatMap(a ->
        bOpt.flatMap(b ->
            Optional(a + b)

 
 Каждый пример отдельный статический метод
 */
public class FlatMapStudy {
    /**
     *  1
     *
     *   Фильтрация в данном случае это преобразование
     *   Из значения Optional.of(-1) типа Optional<Integer>  
     *
     *   В значение Optional.empty() типа Optional<Integer>
     *
     *   Тип данных не меняется, 
     *     значение меняется, 
     *  при том меняется характерно для фильтрации: значение было и его не стало
     *   То есть отфильтровалось
     *
     *  ---
     *  
     *  понимание: 
     *  если значение Integer < 0 - вернуть empty(), иначе - вернуть Integer
     */
    static Optional<Integer> filterPositive(Optional<Integer> num) {
        return num
                .flatMap(value -> {
                    if (value >= 0) {
                        return Optional.of(value);
                    }
                    return Optional.empty();
                });
    }
    
    /**
     *  2
     *
     *  Вторая важная операция преобразование типа
     *  Это смена типа Optional<Integer> в Optional<String>
     *  Меняется параметр типа Integer в String
     */
    static Optional<String> changeIntegerToString(Optional<Integer> num) {
        return num.flatMap(value -> Optional.of(value.toString()));
    }

    /**
     * 1 && 2
     * 1 и 2 - Это две разные по сути операции выполняемые одним методом, покажи мне их четко
     *  Что ты понимаешь разницу, напиши пример
     */
    static Optional<String> filterAndType(Optional<Integer> num) {
        return num
                .flatMap(value -> {
                    if (value >= 0) {
                        return Optional.of(value);
                    }
                    return Optional.empty();
                })
                .flatMap(value -> Optional.of(value.toString()));
    }

    /**
     *  3
     *  цепь вызовов
     *  // абстрактный пример
     *  - дано значение Optional<String>
     *   - проверить на пустоту
     *   - преобразовать Optional<String> -> Optional<Integer>
     *   - фильтровать N >= 0
     *   - преобразовать Optional<Integer> -> Optional<String>
     */
    static Optional<String> flatMapChainConvert(Optional<String> num) {
        return num
            // проверить на пустоту
                .flatMap(value -> {
                    if (value.isEmpty()) {
                        return Optional.empty();
                    }
                    return Optional.of(value);
                })
            // преобразовать Optional<String> -> Optional<Integer>
                .flatMap(value -> {
                    try {
                        Integer n = Integer.parseInt(value);
                        return Optional.of(n);
                    } catch (Exception e) {
                        return Optional.empty();
                    }
                })
            // фильтровать N >= 0
                .flatMap(value -> {
                    if (value >= 0) {
                        return Optional.of(value);
                    }
                    return Optional.empty();
                })
            // преобразовать Optional<Integer> -> Optional<String>
                .flatMap(value -> {
                    return Optional.of(value.toString());
                });
    }
    
    /**
     *  4
     *  цепь вызовов 2
     *
     *  вторая форма flatMap   
     *  Optional<Integer> aOpt = ...
     *  Optional<Integer> bOpt = ...
     *  Optional<Integer> cOpt =
     *     aOpt.flatMap(a ->
     *         bOpt.flatMap(b ->
     *             Optional(a + b)
     *
     *  // абстрактный пример
     *  - даны 2 значения Optional<Integer>
     *   - проверить на пустоту
     *   - выполнить сложение
     *   - вернуть Optional<String>
     */
    static Optional<String> flatMapChainSum(Optional<Integer> num1, Optional<Integer> num2) {
        return num1
                // распаковка N1
                .flatMap(a -> num2
                        // распаковка N2
                        .flatMap(b -> {
                            // сложение
                            int sum = a + b;
                            return Optional.of(sum);
                        })
                        // преобразование в String
                        .flatMap(value -> Optional.of(value.toString())));
    }


    public static void main(String[] args) {
        Optional<Integer> numPos = Optional.of(123);
        Optional<Integer> numNeg = Optional.of(-321);
        Optional<Integer> numEmpty = Optional.empty();

        // 1
        System.out.println("\nФильтрация N >= 0");
        Optional<?> optValue = filterPositive(numPos);
        System.out.println(optValue); // Optional[123]
        System.out.println(optValue.get().getClass()); // class java.lang.Integer
        
        System.out.println(filterPositive(numNeg)); // Optional.empty
        System.out.println(filterPositive(numEmpty)); // Optional.empty
        // проверка полезности Optional
//        Optional<Integer> numNull = null; // скомпилируется
//        System.out.println(filterPositive(numNull)); // ошибка в runtime: Cannot invoke "java.util.Optional.filter(java.util.function.Predicate)" because "num" is null
        
        // 2
        System.out.println("\nИзменение типа");
        optValue = changeIntegerToString(numPos);
        System.out.println(optValue); // Optional[123]
        System.out.println(optValue.get().getClass()); // class java.lang.String
        
        System.out.println(changeIntegerToString(numNeg)); // Optional[-321]
        System.out.println(changeIntegerToString(numNeg).get().getClass()); // class java.lang.String
        System.out.println(changeIntegerToString(numEmpty)); // Optional.empty
        
        // 1+2
        System.out.println("\nФильтрация N >= 0 && Изменение типа");
        optValue = filterAndType(numPos);
        System.out.println(filterAndType(numPos)); // Optional[123]
        System.out.println(optValue.get().getClass()); // class java.lang.String
        
        System.out.println(filterAndType(numNeg)); // Optional.empty
        System.out.println(filterAndType(numEmpty)); // Optional.empty
        
        // 3
        System.out.println("\nцепь вызовов");
        
        Optional<String> numPosStr = Optional.of("123");
        Optional<String> numNegStr = Optional.of("-321");
                
        optValue = flatMapChainConvert(numPosStr);
        System.out.println(flatMapChainConvert(numPosStr)); // Optional[123]
        System.out.println(optValue.get().getClass()); // class java.lang.String
        System.out.println(flatMapChainConvert(numNegStr)); // Optional.empty

        // 4 
        System.out.println("\nцепь вызовов 2");
        
        Optional<Integer> n1 = Optional.of(1);
        Optional<Integer> n2 = Optional.of(2);
        Optional<String> sum = flatMapChainSum(n1, n2);
        
        System.out.println(sum); // Optional[3]
        System.out.println(sum.get().getClass()); // class java.lang.String
    }
}
/*
 ВОПРОС - верно ли следующее утверждение?
 
  Плохо: использовать Optional как тип параметра 
  Optional<Integer> filterPositive(Optional<Integer> num) {
      // ...
  }
      Это плохой тон в ООП-дизайне.
      Optional — это обёртка для возврата, чтобы избежать null.
      Если вы передаёте Optional как параметр — это означает, что вы не уверены, приходит ли значение, но Optional не предназначен для этого.
 
  Хорошо: использовать Integer как параметр, а Optional — как возвращаемое значение 
  Optional<Integer> filterPositive(Integer num)
 */