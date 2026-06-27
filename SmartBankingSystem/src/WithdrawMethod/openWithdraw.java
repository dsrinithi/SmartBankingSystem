public void openWithdraw() {

    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Withdraw Money");
    dialog.setHeaderText("Enter Amount to Withdraw");

    Optional<String> result = dialog.showAndWait();

    result.ifPresent(amountStr -> {

        double amount = Double.parseDouble(amountStr);

        double prev = currentUser.balance;

        // ❌ CHECK BALANCE
        if (amount > currentUser.balance) {
            message.setText("❌ Insufficient Balance!");
            return;
        }

        currentUser.balance -= amount;

        message.setText("✅ Withdrawn ₹" + amount + " | Balance: ₹" + currentUser.balance);

        saveTransaction("Withdraw", amount);
        updateUserFile();
    });
}