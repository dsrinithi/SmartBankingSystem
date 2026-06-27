private void saveTransaction(String type, double amt) {

    try {

        File folder = new File("data");
        if (!folder.exists()) {
            folder.mkdir();
        }

        FileWriter fw = new FileWriter("data/transactions.txt", true);

        String date = LocalDate.now().toString();

        fw.write(
            date + "," +
            type + "," +
            amt + "," +
            currentUser.username + "\n"
        );

        fw.close();

        System.out.println("Transaction Saved Successfully");

    } catch (Exception e) {

        System.out.println("Error Saving Transaction");
        e.printStackTrace();
    }
}