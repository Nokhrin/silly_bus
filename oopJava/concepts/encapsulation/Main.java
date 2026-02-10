package encapsulation;
/*
  Термостат.
  Требованием к состоянию: currentTemp ∈ [minSafe, maxSafe]
  Требованием к поведению: при каждом выходе currentTemp за пределы [warningLow, warningHigh] вызывается alarm.trigger()
  <p>
  Реализуйте без инкапсуляции: публичное поле currentTemp
  Реализуйте с инкапсуляцией: приватное поле + метод adjust(double delta)
  Докажите, что в реализации (1):
  требование к поведению нарушается при прямой модификации currentTemp (нет вызова alarm.trigger())
  требование к состоянию нарушается при controller.currentTemp = 1000
  Покажите, что в реализации (2) нарушение обоих требований возможно только через ошибку внутри adjust, а не извне.
  <p>
  Критерий успеха: В реализации (1) для соблюдения требований при 5 точках модификации температуры требуется ≥ 35 строк дублирующейся логики; в реализации (2) — 12 строк внутри класса, 0 строк в клиентском коде.




в примерах с термометром TemperatureControllerNoEnc нет причин беспокойства, 
от того что поле может быть за пределами допустим значений нет проблем с надежностью - 
т.к. TemperatureControllerNoEnc им по сути никто не пользуется, 
вызовы методов adjust не к чему не приводят, кроме сообщений в консоли - которые можно спокойно игнорить


критика adjust - метод который изменяет что-то , но ничего не возвращает. не возвращает и не генериурет ошибку.

методы объекта должны иметь наглядное и функциональное поведение
а) либо вычислять что-то - т.е. возвращать значение
б) изменять что-то, тогда то, что они изменяют должно быть в аргументах
в) когда работа с внутренним состоянием - например с файлами/бд/... когда файл должен быть не абы какой, а определенного формата
г) не детерминированные методы/функции - когда результат не определен - пользовательский ввод / случайное значение

 */


/**
 * Термостат.
 *  Требованием к состоянию: currentTemp ∈ [minSafe, maxSafe]
 *  Требованием к поведению: при каждом выходе currentTemp за пределы [warningLow, warningHigh] вызывается alarm.trigger()
 *
 * без инкапсуляции: публичное поле currentTemp
 * 
 * 
 * Уточнить требование - поле currentTemp
 *  стартовое значение?
 *  установлен по умолчанию?
 *  установлен в конструкторе?
 *  диапазон значений входит в [warningLow, warningHigh]?
 *  
 */
class TemperatureControllerNoEnc {
    final double minSafe;
    final double maxSafe;

    /**
     * Требованием к состоянию: currentTemp ∈ [minSafe, maxSafe]
     */
    public double currentTemp;

    TemperatureControllerNoEnc(double minSafe, double maxSafe, double currentTemp) {
        this.minSafe = minSafe;
        this.maxSafe = maxSafe;
        this.currentTemp = currentTemp;
    }

}


/**
 * Термостат.
 *  Требованием к состоянию: currentTemp ∈ [minSafe, maxSafe]
 *  Требованием к поведению: при каждом выходе currentTemp за пределы [warningLow, warningHigh] вызывается alarm.trigger()
 *
 * с инкапсуляцией: приватное поле + метод adjust(double delta)
 */
class TemperatureControllerEnc {
    private final double minSafe;
    private final double maxSafe;

    /**
     * Требованием к состоянию: currentTemp ∈ [minSafe, maxSafe]
     */
    private double currentTemp;

    TemperatureControllerEnc(double minSafe, double maxSafe, double currentTemp) {
        this.minSafe = minSafe;
        this.maxSafe = maxSafe;
        this.currentTemp = currentTemp;
    }

    /**
     * Требованием к поведению: при каждом выходе currentTemp за пределы [warningLow, warningHigh] вызывается alarm.trigger()
     */
    void adjust(double delta) {
        this.currentTemp += delta;
        if (this.currentTemp < minSafe || this.currentTemp > maxSafe) {
            Alarm.trigger();
        }
    }

    /**
     * Требованием к поведению: при каждом выходе currentTemp за пределы [warningLow, warningHigh] вызывается alarm.trigger()
     * Требование нарушено.
     */
    void adjustBroken(double delta) {
        this.currentTemp += delta;
        if (this.currentTemp == 42) {
            Alarm.trigger();
        }
    }

}

/**
 * Сигнализация.
 */
class Alarm {
    public static void trigger() {
        System.out.println("Alarm: температура вне нормы");
    }
}


class Main {
    public static void main(String[] args) {
        // БЕЗ ИНКАПСУЛЯЦИИ
        // требование к поведению нарушается при прямой модификации currentTemp (нет вызова alarm.trigger())
        TemperatureControllerNoEnc temperatureControllerNoEnc = new TemperatureControllerNoEnc(15.0, 25.0, 20);
        // поле открыто для записи, решение для реализации требований 
        // - проверять в клиентском коде, здесь
        // - проверять необходимо при каждом изменении поля -> код проверки будет повторяться
        // 1 изменение currentTemp порождает +4 строки кода
        temperatureControllerNoEnc.currentTemp = 42.0;
        if (temperatureControllerNoEnc.currentTemp < temperatureControllerNoEnc.minSafe || 
                temperatureControllerNoEnc.currentTemp > temperatureControllerNoEnc.maxSafe) {
            Alarm.trigger();
        }
        
        temperatureControllerNoEnc.currentTemp = 1000.0;
        // не реализована проверка -> требование к состоянию нарушается

        // С ИНКАПСУЛЯЦИЕЙ
        TemperatureControllerEnc temperatureControllerEnc = new TemperatureControllerEnc(15.0, 25.0, 20);
        // проверка в методе - код проверки не будет повторяться, в клиентском коде нет проверок
        temperatureControllerEnc.adjust(1);
        temperatureControllerEnc.adjust(10);
        // нарушение обоих требований возможно только через ошибку внутри adjust, а не извне.
        temperatureControllerEnc = new TemperatureControllerEnc(15.0, 25.0, 20);
        temperatureControllerEnc.adjustBroken(-10);  // нет ожидаемого поведения
        temperatureControllerEnc.adjustBroken(32);  // ожидаемое поведение, но нарушает требования
    }

}