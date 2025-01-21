public class LocalTester {
    public static TestHandler testHandler = new TestHandler();
    public static void main(String[] ar1gs) {
        StdOut.println("Welcome to the Localized Tester!");
        TesterQuestionEnum questionSelected = null;
        String input = "";
        In in = new In();
        TesterQuestionEnum[] questions = TesterQuestionEnum.values();
        while (questionSelected == null) {
            StdOut.println("Please enter the question or question number you would like to test:\n");
            for (int i = 0; i < questions.length; i++) {
                StdOut.println((i + 1) + ". " + questions[i] + " (" + questions[i].getQuestion() + ")");
            }
            StdOut.println((questions.length + 1) + ". LinkedList");
            StdOut.println((questions.length + 2) + ". MemorySpace");
            StdOut.println((questions.length + 3) + ". All");
            
            StdOut.println("");
            input = in.readLine();
            if (input.toLowerCase().replaceAll(" ", "").equals("all") || 
                input.toLowerCase().replaceAll(" ", "").equals("linkedlist") ||
                input.toLowerCase().replaceAll(" ", "").equals("memoryspace") ||
                input.equals((questions.length + 1) + "") ||
                input.equals((questions.length + 2) + "") ||
                input.equals((questions.length + 3) + "")) {
                break;
            }
            questionSelected = isValidQuestion(input);
        }
        if (input.toLowerCase().replaceAll(" ", "").equals("all") || input.equals((questions.length + 3) + "")) {
            for (int i = 0; i < questions.length; i++) {
                testHandler.questionDecider(questions[i], new String[0]);
            }
        } else if (input.toLowerCase().replaceAll(" ", "").equals("linkedlist") || input.equals((questions.length + 1) + "")) {
            String[] questionArgs = new String[0];
            for (int i = 0; i < questions.length; i++) {
                if (questions[i].getQuestion().toLowerCase().indexOf("linkedlist class") != -1) {
                    testHandler.questionDecider(questions[i], questionArgs);
                }
            }
        } else if (input.toLowerCase().replaceAll(" ", "").equals("memoryspace") || input.equals((questions.length + 2) + "")) {
            String[] questionArgs = new String[0];
            for (int i = 0; i < questions.length; i++) {
                if (questions[i].getQuestion().toLowerCase().indexOf("memoryspace class") != -1) {
                    testHandler.questionDecider(questions[i], questionArgs);
                }
            }
        } else {
            String[] questionArgs = new String[0];
            testHandler.questionDecider(questionSelected, questionArgs);
        }
        testHandler.conclusion();
    }


    public static TesterQuestionEnum isValidQuestion(String input) {
        TesterQuestionEnum[] questions = TesterQuestionEnum.values();
        TesterQuestionEnum questionSelected = null;
        for (int i = 0; i < questions.length; i++) {
            try {
                questionSelected = TesterQuestionEnum.valueOf(input);
                break;
            } catch (Exception e) {
                if (questions[i].getQuestion().toLowerCase().equals(input.toLowerCase()) || input.equals((i + 1) + "")) {
                    questionSelected = questions[i];
                    break;
                }
            }
        }
        return questionSelected;
    }
}
