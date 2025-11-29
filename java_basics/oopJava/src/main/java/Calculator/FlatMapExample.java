package Calculator;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * понять 
 * - назначение
 * - применение
 * - отличие от map
 * 
 */
public class FlatMapExample {
    
    public static void main(String[] args) {
        Optional<String> optOperation = Optional.of("deposit");

        // формирую новую строку с помощью лямбда-выражения, переданного как аргумент map
        // идея - анонимная функция, принимает аргумент (может быть без аргумента), 
        // выполняет логику, декларированную в теле
        // профит - не надо объявлять отдельно - экономия места и наглядность логики 
        // - весь код в той области, где он применяется
        // - чистота - не меняет существующие объекты, в области видимости лямбды свои объекты,
        // состояние других областей видимости сохраняется
        
        // возможно, результат null
        Optional<Optional<String>> result1 = optOperation.map(
                op -> Optional.of("выполнить " + op)
        );
        System.out.println(result1); // Optional[Optional[выполнить deposit]]
        // значение сформировано, но с ним неудобно работать из-за вложенности контейнеров
        
        // решение - распаковка с помощью flatMap
        // Optional<Optional<T>> -> Optional<T>
        Optional<String> result = optOperation.flatMap(
                op -> Optional.of("выполнить " + op)
        );
        System.out.println(result);  // Optional[выполнить deposit]
        
        // зачисление суммы
        Optional<Integer> amount = Optional.of(Integer.parseInt("300"));
        
        // Optional<T> -> map(F) -> Optional<Optional<R>>
        // Optional<T> -> flatMap(F) -> Optional<R>

        // композиция
        // flatMap - функция высшего порядка, то есть, функция, принимающая другие функции как параметры
        // flatMap вызывается над контейнером и применяет переданную функцию к объектам контейнера
        // flatMap возвращает результат в одном контейнере, без вложенности
        // flatMap = мэппинг+распаковка
        
        // пример - сольем списки - преобразуем список списков в один список
        List<List<String>> listOfLists = Arrays.asList(
                Arrays.asList("aaa", "bbb"),
                Arrays.asList("ccc", "ddd", "eee"),
                Arrays.asList("zz", "xx","cc", "vv")
        );
        // преобразую в поток - к объектам потока поочередно применяю лямбду - формирую список
        List<String> result2 = listOfLists.stream()
                .map(list -> list.stream()) // вложенный список
                .flatMap(s -> s)  // элемент списка
                .collect(Collectors.toList());
        
        System.out.println(result2); // [aaa, bbb, ccc, ddd, eee, zz, xx, cc, vv]

        // синтаксический сахар flatMap
        List<String> result3 = listOfLists.stream()
                        .flatMap(List::stream)  // преобразование вложенного списка в поток и распаковка элементов списка в одну строку
                        .collect(Collectors.toList());
        System.out.println(result3); // [aaa, bbb, ccc, ddd, eee, zz, xx, cc, vv]


        Transfer transfer1 = new Transfer("1", "2", BigDecimal.valueOf(500));
        System.out.println(transfer1); // Transfer[sourceId=1, targetId=2, amount=500]
        System.out.println(performTransfer(transfer1)); // Optional[переведено 500 единиц со счета 1 на счет 2]
        
        // распаковка контейнера + для каждого распакованного элемента вызвать метод println объекта System.out
        performTransfer(transfer1).ifPresent(System.out::println); // переведено 500 единиц со счета 1 на счет 2


        /**
         * вывод
         * 
         * использовать flatMap для избежания вложенности 
         *  - например, в последовательности преобразований при парсинге
         *  - в частности, при реализации 
         *  @see ParserCombinator
         */
    }


    // композиция функций - последовательное применение функций - построение составной/сложной функции
    // задача - выполнить перевод
    // дано - необходимые значения для выполнения операции перевода
    // проблема - каждое значение находится в контейнере, требуется преобразование/распаковка контейнера
    // решение - flatMap для каждого значения

    // контейнер Optional позволяет избежать явной проверки на null
    // flatMap позволяет избежать явных действий по распаковке
    public static Map<String, BigDecimal> accounts = new HashMap<>();
    static {
        accounts.put("1", BigDecimal.valueOf(1000));
        accounts.put("2", BigDecimal.valueOf(2000));
    }
    record Transfer(String sourceId, String targetId, BigDecimal amount) {}

    public static Optional<String> performTransfer(Transfer transfer) {
        // выполняю цепочку применений
        return Optional.of(transfer)
                .flatMap(t -> { // обращаюсь к полю amount экземпляра Transfer
                    if (t.amount().compareTo(BigDecimal.ZERO) <= 0) {
                        return Optional.empty(); // сумма должна быть >0
                    }
                    return Optional.of(t); // ???
                })
                .flatMap(t -> {
                    
                    BigDecimal sourceBalance = accounts.getOrDefault(t.sourceId(), BigDecimal.ZERO);
                    BigDecimal targetBalance = accounts.getOrDefault(t.targetId(), BigDecimal.ZERO);
                    // обновляю балансы
                    accounts.put(t.sourceId(), sourceBalance.subtract(t.amount()));
                    accounts.put(t.targetId(), targetBalance.add(t.amount()));
                    return Optional.of("переведено " + t.amount() + " единиц со счета " + t.sourceId() + " на счет " + t.targetId());
                });
    }

}
