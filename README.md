# Expense Manager

Expense Manager is an Android application designed to help users manage their personal finances by tracking income, expenses, accounts, categories, and transaction history in one place.

The application allows users to create multiple accounts, record income and expenses for individual accounts, manage transaction categories, view financial statistics, and switch between light and dark themes.

## Features

### Account Management

* Create multiple financial accounts.
* Set an initial balance for each account.
* Select between different accounts.
* Edit account names.
* Delete accounts.
* Transactions are associated with their respective accounts.
* Each account maintains its own transaction history and balance.

### Income Management

Users can add income transactions using categories such as:

* Allowance
* Award
* Bonus
* Dividend
* Investment
* Lottery
* Salary
* Tips
* Business
* Others

Each income transaction can store information such as:

* Amount
* Category
* Wallet
* Description
* Date

### Expense Management

Users can record expenses using categories such as:

* Bills
* Cloth
* Education
* Entertainment
* Fitness
* Food
* Gifts
* Health
* Furniture
* Pet

Each expense is linked to the currently selected account.

### Transaction Management

The application provides a transaction history for the selected account.

Users can:

* View income and expense transactions.
* See transaction categories.
* See wallets used for transactions.
* View transaction dates.
* Distinguish income and expenses through their displayed amounts.
* Delete transactions.

Transactions are stored locally using the Room database.

### Statistics

The Statistics screen provides an overview of the selected account, including:

* Opening balance
* Total income
* Total expenses
* Current balance
* Expense breakdown by category
* Expense percentages
* Visual expense chart

Statistics are calculated separately for each account.

### Category Management

The application provides separate category management screens for income and expenses.

Users can:

* View available income categories.
* View available expense categories.
* Add custom categories.
* Delete categories.

Income and expense categories are managed separately.

### Multiple Account Support

The application supports multiple accounts.

For example:

```text
Test 1
Initial Balance: Rs. 10,000
Transactions: Multiple

Test 2
Initial Balance: Rs. 5,000
Transactions: None
```

When an expense of Rs. 1,000 is added while Test 2 is selected, the transaction belongs only to Test 2.

This account-based separation prevents transactions from appearing under the wrong account.

### Themes

The application supports:

* System Default
* Light Mode
* Dark Mode

UI colors are designed to adapt to the selected theme.

### Language Support

The application includes language selection through the settings menu.

Currently supported languages include:

* English
* Urdu

## Application Structure

The application is built using a Fragment-based architecture.

The main application contains:

```text
HomeContainerFragment
│
├── Transactions
├── Add
│   ├── Income
│   └── Expense
│
├── Statistics
│
└── Settings
    ├── Accounts
    └── Categories
        ├── Income Categories
        └── Expense Categories
```

## Database

The application uses **Room Database** for local data storage.

The database currently contains:

### Account Table

Stores account information such as:

* Account ID
* Account name
* Currency
* Initial amount

### Transaction Table

Stores transaction information such as:

* Transaction ID
* Account ID
* Transaction type
* Amount
* Description
* Wallet
* Category
* Date

The `accountId` connects each transaction to its corresponding account.

For example:

```text
Account
ID: 2
Name: Test 2

Transaction
ID: 15
Account ID: 2
Type: EXPENSE
Category: Food
Amount: 1000
```

This ensures that the transaction belongs to Test 2.

## Technologies Used

* **Kotlin**
* **Android SDK**
* **Android Studio**
* **XML Layouts**
* **Room Database**
* **SQLite**
* **Android Jetpack**
* **Navigation Component**
* **View Binding**
* **LiveData**
* **Coroutines**
* **RecyclerView**
* **Material Design Components**

## Project Package

```text
com.example.expensemanager
```

## Main Components

Some of the important components in the application include:

```text
HomeContainerFragment
TranscationFragment
AddExpense
IncomeFragment
StatisticsFragment

SettingAccount
settingCategory
settingExpense
SettingIncome

Categories
ExpenseCategory
IncomeCategory

Roomdatabase_UserDatabase
RoomdatabaseUserdata
RoomdatabaseTransaction

AccountAdapter
TransactionAdapter
SettingTransactionAdapter

ThemeManager
ExpenseManagerApplication
```

## Data Flow

A typical expense transaction follows this flow:

```text
User selects account
        ↓
User opens Add Expense
        ↓
User selects Expense
        ↓
User selects category
        ↓
User enters amount/details
        ↓
Transaction is created
        ↓
Transaction is stored in Room Database
        ↓
Transaction is linked to selected account
        ↓
Transaction appears in Transactions
        ↓
Statistics are updated
```

## Balance Calculation

The current account balance is calculated using:

```text
Current Balance =
Initial Amount
+ Total Income
- Total Expenses
```

For example:

```text
Initial Amount = Rs. 5,000
Income         = Rs. 2,000
Expenses       = Rs. 1,000

Current Balance = Rs. 6,000
```

## UI

The application contains the following main areas:

### Home

Displays the selected account and provides access to:

* Transactions
* Add Income/Expense
* Statistics

### Add Transaction

Allows users to add:

* Income
* Expense

### Statistics

Provides a visual overview of the account's financial activity.

### Settings

Provides access to:

* Account management
* Category management
* Theme settings
* Language settings

## Requirements

To build and run the project, you need:

* Android Studio
* Android SDK
* Kotlin
* An Android device or emulator

## Installation

1. Clone the repository:

```bash
git clone <repository-url>
```

2. Open the project in Android Studio.

3. Allow Android Studio to download and synchronize the required Gradle dependencies.

4. Connect an Android device or start an emulator.

5. Build and run the application.

## Local Storage

Expense Manager stores its financial data locally on the device using Room Database.

No external server is required for the core expense and income tracking functionality.

## Future Improvements

Possible future improvements include:

* Cloud synchronization
* User authentication
* Automatic backup and restore
* More advanced financial reports
* Custom wallet management
* Monthly and yearly reports
* Export transactions to PDF or Excel
* Search and filtering for transactions
* Budget management
* Recurring transactions
* More customization options

## Project Status

The application is currently under development.

Core functionality including account management, income and expense tracking, transaction management, category management, statistics, themes, and account-specific data handling has been implemented.

## Author

**Zaid Ahmad**

Software Engineering Student

---

## License

This project is currently intended for educational and personal development purposes.
