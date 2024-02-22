# LocalLogger Android
### This library can be used to collect, display, and share developer logs on client devices.

To make it work you can add a new module to your project and copy content of this library or add this repository as a child repo.
The application stores logs and sessions in a local db data.

### Step 1: init Application log libraries.
Initialization is required to initialize the local library and data sources.
> AppLogLibrary.init(this)

### Step2: Create new log sessions
It's required to create a session instance, so all logs will be linked to a specific session.
> SessionDataSource.createNewSession()
#### This is a suspend function.

### Step 3: Add new logs.
There are a few types of logs that you can track:
> INFO - used to track general information
> WARNING - used to track developer warning
> ERROR - used to track error cases
> IN_MESSAGE - used for messages received on Mobile phone
> OUT_MESSAGE - used for messages sent from Mobile phone

**LogsDataSource** - is a main class to collect and add logs.
It has several method calls to add logs into the system.

#### warning messages
You can also add optional **class name** and **method name** for additional debug purposes.
> suspend fun w(className: String, methodName: String, message: String)
> suspend fun w(message: String)

#### info messages
You can also add optional **class name** and **method name** for additional debug purposes.
> suspend fun i(className: String, methodName: String, message: String)
> suspend fun i(message: String)

#### warning messages
You can also add optional **class name** and **method name** for additional debug purposes.
> suspend fun w(className: String, methodName: String, message: String)
> suspend fun w(message: String)

#### error messages
You can also add optional **class name** and **method name** for additional debug purposes.
> suspend fun e(className: String, methodName: String, message: String)
> suspend fun e(message: String)

#### input messages
You can also add optional **class name** and **method name** for additional debug purposes.
> suspend fun inMessage(className: String, methodName: String, message: String)
> suspend fun inMessage(message: String)

#### output messages
You can also add optional **class name** and **method name** for additional debug purposes.
> suspend fun outMessage(className: String, methodName: String, message: String)
> suspend fun outMessage(message: String)

### Step 4: view collected messages
The library includes **LogsActivity** that can display collected messages for the active session, a list of previous sessions, and session details. The app can open it by executing the next command.

> startActivity(Intent(this, LogsActivity::class.java))

The library also includes **LogDialogFragment**. It's a dialog fragment that can display current session logs.

> LogDialogFragment().show(  
supportFragmentManager, LogDialogFragment.TAG)

### Conclusions

The library can be used to collect logs, help to understand issues on the user side, and provide access for the end user to view and share logs. 
