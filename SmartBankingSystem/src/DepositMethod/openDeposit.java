public void openDeposit() {

    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Deposit Money");
    dialog.setHeaderText("Enter Amount to Deposit");

    Optional<String> result = dialog.showAndWait();

    result.ifPresent(amountStr -> {

        double amount = Double.parseDouble(amountStr);

        double prev = currentUser.balance;
        currentUser.balance += amount;

        message.setText("✅ Deposited ₹" + amount + " | Balance: ₹" + currentUser.balance);

        saveTransaction("Deposit", amount);
        updateUserFile();
    });
}