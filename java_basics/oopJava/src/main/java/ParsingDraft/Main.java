package ParsingDraft;

public class Main {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        OperationService service = new OperationService();

        String[] commands = {
                "deposit 1000",
                "withdraw 100",
                "transfer 200 from 1 to 2",
        };

        for (String cmd : commands) {
            try {
                Operation op = parser.parse(cmd);
                System.out.println("Распознана команда: " + op);
                service.execute(op);
            } catch (Exception e) {
                System.err.println("Ошибка при обработке: " + cmd);
            }
        }

        /*
        Распознана команда: Операция{тип:FinancialOperationsDraft.Deposit,сумма:1000,источник:0,получатель:0}
        Баланс: 1000
        Распознана команда: Операция{тип:Withdraw,сумма:100,источник:0,получатель:0}
        Распознана команда: Операция{тип:FinancialOperationsDraft.Transfer,сумма:200,источник:1,получатель:2}
        Ошибка при обработке: withdraw 100
        Ошибка при обработке: transfer 200 from 1 to 2
         */
    }
}
