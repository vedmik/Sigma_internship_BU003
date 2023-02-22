# My self code convention:
___
___
# 1. S.O.L.I.D. Violation:
## S - Single Responsibility Principle

"There should never be more than one reason for a class to change."

In other words:
Every class should have only one responsibility.

### Bad practice:
```java
public class Employee {
    private String name;
    private int id;
    private double salary;

    public void saveEmployeeDataToDatabase() {
        // code to save employee data to database
    }

    public void calculateSalary() {
        // code to calculate employee's salary
    }
}
```
### Good practice:
```java
public class Employee {
    private String name;
    private int id;
    private double salary;

    // getters and setters for name, id and salary

}

public class EmployeeDatabase {
    public void saveEmployeeDataToDatabase(Employee employee) {
        // code to save employee data to database
    }
}

public class EmployeeSalaryCalculator {
    public void calculateSalary(Employee employee) {
        // code to calculate employee's salary
    }
}
```
## O — Open/closed principle

"Software Entities should be open for extension, but closed for modification."

You should never change anything, always adding new behavior by static or dynamic polymorphism. 
By doing so you minimize change in your code base, thus minimizing the chance of breaking things.

### Bad practice:
```java
public class Shape {
    private String type;

    public Shape(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public double calculateArea() {
        double area = 0.0;
        if (type.equalsIgnoreCase("circle")) {
            int radius = 10;
            area = Math.PI * Math.pow(radius, 2);
        } else if (type.equalsIgnoreCase("rectangle")) {
            int width = 5;
            int height = 10;
            area = width * height;
        }
        return area;
    }
}
```
### Good practice:
```java
public abstract class Shape {
    public abstract double calculateArea();
}

public class Circle extends Shape {
    private int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * Math.pow(radius, 2);
    }
}

public class Rectangle extends Shape {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }
}

```

## L — Liskov substitution principle

"Functions that use pointers or references to base classes must be able to use objects of derived classes without knowing it."

You should be able to substitute any parent class with any of their children without any behavior modification.

### Bad practice:
```java
public class Rectangle {
    private int width;
    private int height;

    // constructor, getters and setters for width, height

    public int calculateArea() {
        return width * height;
    }
}

public class Square extends Rectangle {
    private int side;

    public Square(int side) {
        super(side, side);
        this.side = side;
    }

    @Override
    public void setWidth(int width) {
        this.side = width;
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    public void setHeight(int height) {
        this.side = height;
        super.setWidth(height);
        super.setHeight(height);
    }
}
```

### Good practice:
```java
public interface Shape {
    public int calculateArea();
}

public class Rectangle implements Shape {
    private int width;
    private int height;

    // constructor, getters and setters for width, height

    @Override
    public int calculateArea() {
        return width * height;
    }
}

public class Square implements Shape {
    private int side;

    // constructor, getter and setter for side

    @Override
    public int calculateArea() {
        return side * side;
    }
}

```

## I — Interface segregation principle

"Clients should not be forced to depend upon interfaces that they do not use."

Many client specific interfaces are better than a big one. 
An interface should be the minimal set of behaviors (or functions) you need.

### Bad practice:
```java
public interface Vehicle {
    void start();
    void stop();
    void accelerate();
    void brake();
}

public class Car implements Vehicle {
    @Override
    public void start() {
        // implementation
    }

    @Override
    public void stop() {
        // implementation
    }

    @Override
    public void accelerate() {
        // implementation
    }

    @Override
    public void brake() {
        // implementation
    }
}

public class Bicycle implements Vehicle {
    @Override
    public void start() {
        // implementation
    }

    @Override
    public void stop() {
        // implementation
    }

    @Override
    public void accelerate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void brake() {
        throw new UnsupportedOperationException();
    }
}

```
### Good practice:
```java
public interface Vehicle {
    void start();
    void stop();
}

public interface MotorVehicle extends Vehicle {
    void accelerate();
    void brake();
}

public class Car implements MotorVehicle {
    @Override
    public void start() {
        // implementation
    }

    @Override
    public void stop() {
        // implementation
    }

    @Override
    public void accelerate() {
        // implementation
    }

    @Override
    public void brake() {
        // implementation
    }
}

public class Bicycle implements Vehicle {
    @Override
    public void start() {
        // implementation
    }

    @Override
    public void stop() {
        // implementation
    }
}

```

## D - Dependency inversion principle

"Depend upon abstractions, not concretions."

Your project shouldn’t depend of anything, make those things depend of interfaces. 
You should design a wrapper around your dependency.

### Bad practice:

```java
public class Backend {
    public void doSomething() {
        // implementation
    }
}

public class Frontend {
    private Backend backend = new Backend();

    public void doSomething() {
        backend.doSomething();
    }
}
```
### Good practice:
```java
public interface Backend {
    void doSomething();
}

public class BackendImpl implements Backend {
    @Override
    public void doSomething() {
        // implementation
    }
}

public class Frontend {
    private Backend backend;

    public Frontend(Backend backend) {
        this.backend = backend;
    }

    public void doSomething() {
        backend.doSomething();
    }
}
```
___
___
# 2. OPTIONAL, LAMBDA, STREAM API

# 2.1 OPTIONAL - how to use:
## 2.1.1 Never assign null to an optional variable.
### Bad practice:
```java
public Optional<Employee> getEmployee(int id) {
    // perform a search for employee 
    Optional<Employee> employee = null; // in case no employee
    return employee; 
}
```
### Good practice:
```java
public Optional<Employee> getEmployee(int id) {
        // perform a search for employee
        Optional<Employee> employee = Optional.empty(); // in case no employee
        return employee;
}
```
## 2.1.2 Don’t call get() directly.
### Bad practice:
```java
Optional<Employee> employee = HRService.getEmployee();
Employee myEmployee = employee.get();
```
### Good practice:
```java
if (employee.isPresent()) {
    Employee myEmployee = employee.get();
    ... // do something with "myEmployee"
} else {
    ... // do something that doesn't call employee.get()
}
```
## 2.1.3 Don’t use null directly to get a null reference when you have an Optional.
### Bad practice:
```java
Optional<Employee> employee = ...
        
if (employee.isPresent()) {
    method.some(employee.get(), "Test");
} else {
    method.some(null, "Test");
}
```
### Good practice:
```java
Optional<Employee> employee = ...
        
method.some(employee.orElse(null), "Test");
```

## 2.1.4 Avoid using an isPresent() and get() pair for setting and returning a value
### Bad practice:
```java
Optional<EmpStatus> empStatus = ...
        
if (empStatus.isPresent()) {
    return empStatus.get();
} else {
    return DEFAULT_STATUS;
}
```
### Good practice:
```java
Optional<EmpStatus> empStatus = ...
        
return empStatus.orElse(DEFAULT_STATUS);
```
## 2.1.5 Don’t use orElse() for returning a computed value.
### Bad practice:
```java
Optional<Employee> getFromCache(int id) {
    System.out.println("search in cache with Id: " + id);
    // get value from cache
}

Optional<Employee> getFromDB(int id) {
    System.out.println("search in Database with Id: " + id);
    // get value from database
}

public Employee findEmployee(int id) {
    return getFromCache(id)
        .orElse(getFromDB(id)
            .orElseThrow(() -> 
                new NotFoundException("Employee not found with id" + id)));
}
```
### Good practice:
```java
...
public Employee findEmployee(int id) {
    return getFromCache(id)
        .orElseGet(() -> getFromDB(id)
            .orElseThrow(() -> {
                return new NotFoundException("Employee not found with id" + id);
            }));
}
```
## 2.1.6 Throw an exception in the absence of a value.
### Bad practice:
```java
public Employee findEmployee(int id) {
    var employee = p.getFromDB(id);
    
    if(employee.isPresent()) {
        return employee.get();
    } else {
        throw new NoSuchElementException("Employee not found with id " + id);
    } 
}
```
### Good practice:
```java
public Employee findEmployee(int id) {
    return getFromDB(id).orElseThrow(() -> new NoSuchElementException("Employee not found with id " + id));
}
```
## 2.1.7 Don’t use isPresent()-get() if you want to perform an action only when an Optional value is present.
### Bad practice:
```java
Optional<String> confName = Optional.of("CodeOne");
if(confName.isPresent()) {
    System.out.println(confName.get().length());
}
```
### Good practice:
```java
Optional<String> confName = Optional.of("CodeOne");
confName.ifPresent(s -> System.out.println(s.length()));
```
## 2.1.8 Don’t use isPresent()-get() to execute an empty-based action if a value is not present.
### Bad practice:
```java
Optional<Employee> employee = ... ;

if(employee.isPresent()) {
    log.debug("Found Employee: {}" , employee.get().getName());
} else {
    log.error("Employee not found");
}
```
### Good practice:
```java
Optional<Employee> employee = ... ;

employee.ifPresentOrElse(
        emp -> log.debug("Found Employee: {}", emp.getName()),
        () -> log.error("Employee not found"));
```
## 2.1.9 Return another Optional when no value is present.
### Bad practice:
```java
Optional<String> defaultJobStatus = Optional.of("Not started yet.");

public Optional<String> fetchJobStatus(int jobId) {
    Optional<String> foundStatus = ... ; // fetch declared job status by id
        
    if (foundStatus.isPresent()) {
        return foundStatus;
    } else {
        return defaultJobStatus;        
    }
}
```
### Good practice:
```java
public Optional<String> fetchJobStatus(int jobId) {
    Optional<String> foundStatus = ... ; // fetch declared job status by id
        
    return foundStatus.or(() -> Optional.of("Not started yet."));
}
```

## 2.1.10 Get an Optional’s status regardless of whether it is empty.
### Bad practice:
```java
public boolean isMovieListEmpty(int id){
    Optional<MovieList> movieList = ... ;
    
    return !movieList.isPresent();
}
```
### Good practice:
```java
public boolean isMovieListEmpty(int id){
    Optional<MovieList> movieList = ... ;

    return movieList.isEmpty();
}
```

## 2.1.11 Don’t overuse Optional.
### Bad practice:
```java
public String fetchJobStatus(int jobId) {
    String status = ... ; // fetch declared job status by id
    
    return Optional.ofNullable(status).orElse("Not started yet.");
}
```
### Good practice:
```java
public String fetchJobStatus(int jobId) {
    String status = ... ; // fetch declared job status by id

    return status == null ? "Not started yet." : status;
}
```
___
# 2.2 LAMBDA Expressions 

Lambda expression provides implementation of functional interface.
An interface which has only one abstract method is called functional interface.
Java provides an anotation `@FunctionalInterface`, which is used to declare an interface as functional interface.

## 2.2.1 Java Lambda Expression Syntax
```java
(argument-list) -> {body}  
```
Java lambda expression is consisted of three components.

1) **Argument-list:** It can be empty or non-empty as well.

2) **Arrow-token:** It is used to link arguments-list and body of expression.

3) **Body:** It contains expressions and statements for lambda expression.

No Parameter Syntax
```java
() -> {  
//Body of no parameter lambda  
} 
``` 
One Parameter Syntax
```java
p1 -> {  
//Body of single parameter lambda  
}  
```
Two Parameter Syntax
```java
(p1,p2) -> {  
//Body of multiple parameter lambda  
}
```

## 2.2.2 Prefer Standard Functional Interfaces
Functional interfaces, which are gathered in the `java.util.function` package, satisfy most developers’ needs in providing target types for lambda expressions and method references. Each of these interfaces is general and abstract, making them easy to adapt to almost any lambda expression. Developers should explore this package before creating new functional interfaces.

```java
class App {
    
    @FunctionalInterface
    public interface Todo {
        String method(String string);
    }

    public static String add(String string, Todo todo) {
        return todo.method(string);
    }

    public static void main(String[] args) {
        Todo todo = parameter -> parameter + " from lambda";
        String result = App.add("I've added something", todo);
    }
}
```
Look closer and you will see that Todo is nothing more than a function that accepts one argument and produces a result. 
Java 8 already provides such an interface in `Function<T,R>` from the `java.util.function` package.

Now we can remove interface Todo completely and change our code to:
```java
class App {
    
    public static String add(String string, Function<String, String> fn) {
        return fn.apply(string);
    }

    public static void main(String[] args) {
        Function<String, String> fn = parameter -> parameter + " from lambda";
        String result = App.add("I've added something", fn);    
    }
}
```
## 2.2.3 Use Method References
```java
a -> a.toLowerCase(); //Less readable

String::toLowerCase;  //More readable
```
## 2.2.4 Avoid Parentheses Around a Single Parameter
```java
(a) -> a.toLowerCase(); //Less readable

a -> a.toLowerCase();   //More readable
```
## 2.2.5 Avoid Specifying Parameter Types
```java
(String a, String b) -> a.toLowerCase() + b.toLowerCase(); //Less readable

(a, b) -> a.toLowerCase() + b.toLowerCase();               //More readable
```
## 2.2.6 Java Lambda Expression Examples:
### 2.2.6.1 Simple example 

Without lambda, Drawable implementation using anonymous class 
```java
interface Drawable{  
    public void draw();  
} 

public class LambdaExample {  
    public static void main(String[] args) {  
        int width = 10;
        
        Drawable d = new Drawable(){  
            public void draw() {
                System.out.println("Drawing " + width);
            }  
        };  
        d.draw();  
    }  
}
```
With lambda
```java
@FunctionalInterface  
interface Drawable{  
    public void draw();  
}  
  
public class LambdaExample {  
    public static void main(String[] args) {  
        int width = 10;  
        
        Drawable d = () -> {  
            System.out.println("Drawing " + width);  
        };  
        d.draw();  
    }  
}
```
With `java.util.function.Supplier`. 
```java
public class LambdaExample {  
    public static void main(String[] args) {  
        int width = 10;

        Supplier d = () -> {  
            System.out.println("Drawing " + width);  
        };  
        d.get();  
    }  
}
```
### 2.2.6.2 No Parameter example

```java
interface Sayable{  
    public String say();  
}

public class LambdaExample {  
    public static void main(String[] args) {
        Sayable s = () -> {  
            return "I have nothing to say.";  
        };  
        System.out.println(s.say());  
    }  
}
```
With `java.util.function.Supplier` package.
```java
public class LambdaExample {  
    public static void main(String[] args) {
        Supplier<String> s = () -> "I have nothing to say.";
        
        System.out.println(s.get());  
    }  
}
```

### 2.2.6.3 Single parameter example
```java
interface Sayable{  
    public String say(String name);  
}  
  
public class LambdaExample {  
    public static void main(String[] args) {
        
        Sayable s = name -> "Hello, " + name;
        System.out.println(s.say("Sonoo"));  
    }  
}
```
With `java.util.function.Function` package.
```java
public class LambdaExample {  
    public static void main(String[] args) {
        
        Function<String, String> s = name -> "Hello, " + name;
        System.out.println(s.apply("Sonoo"));  
    }  
}
```
### 2.2.6.4 
```java
interface Addable{  
    int add(int a,int b);  
}  
  
public class LambdaExpressionExample5{  
    public static void main(String[] args) {
        Addable add = (a, b) -> (a + b);  
        System.out.println(add.add(10, 20));
    }  
}
```
With `java.util.function.ToIntBiFunction` package.
```java
public class LambdaExpressionExample5{  
    public static void main(String[] args) {
        ToIntBiFunction<int, int> add = (a, b) -> (a + b);  
        System.out.println(add.applyAsInt(10, 20));
    }  
}
```
## 2.2.7 BAD PRACTICE LAMBDA
  
### 2.2.7.1 Overusing lambda expressions
#### Bad practice
```java
List<String> names = new ArrayList<>();
// Populate names list...
names.stream().filter(name -> name.length() > 5)
    .map(name -> name.toLowerCase())
    .forEach(name -> System.out.println(name));
```
#### Good practice
```java
List<String> names = new ArrayList<>();
// Populate names list...
names.stream().filter(name -> name.length() > 5)
    .map(String::toLowerCase)
    .forEach(System.out::println);
```
### 2.2.7.2 Using overly complex lambda expressions
#### Bad practice
```java
Function<String, Integer> stringToInt = (String str) -> {
    char[] chars = str.toCharArray();
    int result = 0;
    for (char c : chars) {
        result += c;
    }
    return result;
};
```
#### Good practice
```java
Function<String, Integer> stringToInt = str -> str.chars().sum();
```
### 2.2.7.3 Neglecting to specify parameter types
#### Bad practice
```java
Consumer<String> printString = str -> System.out.println(str);
```
#### Good practice
```java
Consumer<String> printString = (String str) -> System.out.println(str);
```

# 2.3 STREAM API - Best practices

## 2.3.1 Code formatting
### 2.3.1.1 One stream method call per line
```java
// BAD CODE:
strings.stream().filter(s -> s.length() > 2).sorted()
	.map(s -> s.substring(0, 2)).collect(Collectors.toList());

// GOOD CODE:
strings.stream()
	.filter(s -> s.length() > 2)
	.sorted()
	.map(s -> s.substring(0, 2))
	.collect(Collectors.toList());
```
### 2.3.1.2 Use `import static` all of the standard stream related methods.
```java
// BAD CODE:
strings.stream()
	.sorted(Comparator.reverseOrder())
	.limit(10)
	.collect(Collectors.toMap(Function.identity(), String::length));

// GOOD CODE:
strings.stream()
	.sorted(reverseOrder())
	.limit(10)
	.collect(toMap(identity(), String::length));
```
### 2.3.1.3 Prefer method references to lambdas
```java
// AVOID:
strings.stream()
	.map(s -> s.length())
	.collect(toList());

// PREFER:
strings.stream()
	.map(String::length)
	.collect(toList());
```

## 2.3.2 Bad practice Stream

### 2.3.2.1 Not closing the stream after use
```java
List<Integer> myList = Arrays.asList(1, 2, 3);
Stream<Integer> stream = myList.stream();
stream.filter(i -> i % 2 == 0);
// stream not closed
```
### 2.3.2.2 Using parallel streams without considering the overhead
```java
List<Integer> myList = Arrays.asList(1, 2, 3);
Stream<Integer> stream = myList.parallelStream();
stream.filter(i -> i % 2 == 0);
```
### 2.3.2.3 Using infinite streams without considering performance implications
```java
Stream<Integer> infiniteStream = Stream.iterate(0, i -> i + 1);
infiniteStream.filter(i -> i % 2 == 0).limit(10);
```

### 2.3.2.4 Creating a new stream unnecessarily
```java
List<Integer> myList = Arrays.asList(1, 2, 3);
myList.stream().filter(i -> i % 2 == 0);
myList.stream().filter(i -> i % 3 == 0);
```
### 2.3.2.5 Using a stream to iterate over a collection without adding any value
```java
List<Integer> myList = Arrays.asList(1, 2, 3);
Stream<Integer> stream = myList.stream();
stream.forEach(i -> { /* do nothing */ });
```
### 2.3.2.6 Creating a stream from an array instead of using Arrays.stream()
```java
int[] myArray = {1, 2, 3};
Stream<Integer> stream = Stream.of(myArray).mapToObj(Integer::valueOf);
```
### 2.3.2.7 Using Stream.collect() to convert a stream to a list
```java
List<Integer> myList = Arrays.asList(1, 2, 3);
List<Integer> result = myList.stream().collect(Collectors.toList());
```
### 2.3.2.8 Using Stream.collect() to reduce a stream to a single value
```java
List<Integer> myList = Arrays.asList(1, 2, 3);
int sum = myList.stream().collect(Collectors.summingInt(Integer::intValue));
```
### 2.3.2.9 Using Stream.filter() to modify a stream instead of using Stream.map()
```java
List<Integer> myList = Arrays.asList(1, 2, 3);
List<Integer> result = myList.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
```
___
___

# 3. REST naming convention
## 3.1 Resource:
In REST, the primary data representation is called resource.
### 3.1.1 A resource can be a singleton or a collection.
We can identify `customers` collection resource using the URI `/customers`.

We can identify a single `customer` resource using the URI `/customers/{customerId}`.

### 3.1.2 A resource may contain sub-collection resources also.
For example, sub-collection resource `accounts` of a particular `customer` can be identified using the URN 
`/customers/{customerId}/accounts` (in a banking domain).

Similarly, a singleton resource `account` inside the sub-collection resource `accounts` can be identified as follows:
`/customers/{customerId}/accounts/{accountId}`.

### 3.1.3 URI
REST APIs use Uniform Resource Identifiers (URIs) to address resources. 
REST API designers should create URIs that convey a REST API’s resource model to the potential clients of the API. 
When resources are named well, an API is intuitive and easy to use. If done poorly, 
that same API can be challenging to use and understand.

## 3.2 Best Practices
### 3.2.1 Use nouns to represent resources
RESTful URI should refer to a resource that is a thing (noun) instead of referring to an action (verb) 
because nouns have properties that verbs do not have – similarly, resources have attributes.

```bash
http://api.example.com/device-management/managed-devices 
http://api.example.com/device-management/managed-devices/{device-id} 
http://api.example.com/user-management/users
http://api.example.com/user-management/users/{id}
```
🛠 _there are 4 archetypes, don't create hybrid archetypes, pick one and use it throughout the project_
  
### 3.2.1.1 Document
In REST, you can view it as a single resource inside resource collection. A document’s state representation typically includes both fields with values and links to other related resources.

Use “singular” name to denote document resource archetype.
```bash
http://api.example.com/device-management/managed-devices/{device-id}
http://api.example.com/user-management/users/{id}
http://api.example.com/user-management/users/admin
```
### 3.2.1.2 Collection
A collection resource is a server-managed directory of resources.

Clients may propose new resources to be added to a collection. However, it is up to the collection resource to choose to create a new resource or not.

A collection resource chooses what it wants to contain and also decides the URIs of each contained resource.

Use the “plural” name to denote the collection resource archetype.
 ```bash
http://api.example.com/device-management/managed-devices
http://api.example.com/user-management/users
http://api.example.com/user-management/users/{id}/accounts
```
### 3.2.1.3. Store
A store is a client-managed resource repository. A store resource lets an API client put resources in, get them back out, and decide when to delete them.

A store never generates new URIs. Instead, each stored resource has a URI. The URI was chosen by a client when the resource initially put it into the store.

Use “plural” name to denote store resource archetype.
```bash
http://api.example.com/song-management/users/{id}/playlists
```
### 3.2.1.4. controller
A controller resource models a procedural concept. Controller resources are like executable functions, with parameters and return values, inputs, and outputs.

Use “verb” to denote controller archetype.
```bash
http://api.example.com/cart-management/users/{id}/cart/checkout 
http://api.example.com/song-management/users/{id}/playlist/play
```
## 3.2.2. Consistency:
Use consistent resource naming conventions and URI formatting for minimum ambiguity and maximum readability and maintainability.

### 3.2.2.1. Use forward slash `/` to indicate hierarchical relationships
The forward-slash `/` character is used in the path portion of the URI to indicate a hierarchical relationship between resources. e.g.
```bash
http://api.example.com/device-management
http://api.example.com/device-management/managed-devices
http://api.example.com/device-management/managed-devices/{id}
http://api.example.com/device-management/managed-devices/{id}/scripts
http://api.example.com/device-management/managed-devices/{id}/scripts/{id}
```

### 3.2.2.2. Do not use trailing forward slash `/` in URIs
As the last character within a URI’s path, a forward slash `/` adds no semantic value and may confuse. It’s better to drop it from the URI.
```bash
http://api.example.com/device-management/managed-devices   //This is much better version

http://api.example.com/device-management/managed-devices/  //Try not to use this version
```
### 3.2.2.3. Use hyphens `-` to improve the readability of URIs
To make your URIs easy for people to scan and interpret, use the hyphen `-` character to improve the readability of names in long-path segments.
```bash
http://api.example.com/device-management/managed-devices 	//This is much better version

http://api.example.com/devicemanagement/manageddevices/     //Try not to use this version
```

### 3.2.2.4. Do not use underscores `_`
It’s possible to use an underscore in place of a hyphen to be used as a separator – But depending on the application’s font, it is possible that the underscore `_` character can either get partially obscured or completely hidden in some browsers or screens.

To avoid this confusion, use hyphens `-` instead of underscores `_`.
```bash
http://api.example.com/inventory-management/managed-entities/{id}/install-script-location  //More readable

http://api.example.com/inventory-management/managedEntities/{id}/installScriptLocation  //Less readable
http://api.example.com/inventory-management/managedEntities/{id}/install_script_location
```
### 3.2.2.5. Use lowercase letters in URIs
When convenient, lowercase letters should be consistently preferred in URI paths.
```bash
http://api.example.org/my-folder/my-doc       //1
HTTP://API.EXAMPLE.ORG/my-folder/my-doc     //2
http://api.example.org/My-Folder/my-doc       //3
```
In the above examples, 1 and 2 are the same, but 3 is not as it uses My-Folder in capital letters.
## 3.2.3. Do not use file extensions
File extensions look bad and do not add any advantage. Removing them decreases the length of URIs as well. No reason to keep them.

Apart from the above reason, if you want to highlight the media type of API using file extension, then you should rely on the media type, as communicated through the Content-Type header, to determine how to process the body’s content.
```bash
http://api.example.com/device-management/managed-devices.xml    //Do not use it

http://api.example.com/device-management/managed-devices        //This is correct URI
```

## 3.2.4. Never use CRUD function names in URIs
We should not use URIs to indicate a CRUD function. URIs should only be used to uniquely identify the resources and not any action upon them.

We should use HTTP request methods to indicate which CRUD function is performed.
```bash
HTTP GET    http://api.example.com/device-management/managed-devices       //Get all devices
HTTP POST   http://api.example.com/device-management/managed-devices       //Create new Device

HTTP GET    http://api.example.com/device-management/managed-devices/{id}  //Get device for given Id
HTTP PUT    http://api.example.com/device-management/managed-devices/{id}  //Update device for given Id
HTTP DELETE http://api.example.com/device-management/managed-devices/{id}  //Delete device for given Id
```
## 3.2.5. Use query component to filter URI collection
Often, you will encounter requirements where you will need a collection of resources sorted, filtered, or limited based on some specific resource attribute.

For this requirement, do not create new APIs – instead, enable sorting, filtering, and pagination capabilities in resource collection API and pass the input parameters as query parameters. e.g.
```bash
http://api.example.com/device-management/managed-devices
http://api.example.com/device-management/managed-devices?region=USA
http://api.example.com/device-management/managed-devices?region=USA&brand=XYZ
http://api.example.com/device-management/managed-devices?region=USA&brand=XYZ&sort=installation-date
```
____   
# 4. Unit testing

## 4.1 Unit Testing and the Arrange, Act and Assert (AAA) Pattern
The AAA (Arrange-Act-Assert) pattern has become almost a standard across the industry. 
It suggests that you should divide your test method into three sections: 
arrange, act and assert. Each one of them only responsible for the part in which they are named after.

So the `arrange` section you only have code required to setup that specific test.
Here objects would be created, mocks setup (if you are using one) 
and potentially expectations would be set. 
Then there is the `Act`, which should be the invocation of the method being tested. 
And on `Assert` you would simply check whether the expectations were met. More info can be found HERE.

Following this pattern does make the code quite well structured and easy to understand. 
In general lines, it would look like this:
```java
@Test
public void someTest(){
        // arrange
        Part expected = new Part(BRAND, CODE);
        expected.setDescription("111");
        when(partRepository.findById(PART_ID)).thenReturn(Optional.of(testPart));
        when(partRepository.save(expected)).thenReturn(expected);
        
        // act
        Part actual = sut.updatePart(expected);
        
        // assert
        assertEquals(expected, actual);
        verify(partRepository, times(1)).findById(PART_ID);
        verify(partRepository, times(1)).save(expected);
}
```

## 4.2 Mocking Best Practices in Unit tests

### 4.2.1 Don’t write any more mocking code than you need to. 

Mocking code is still “more code” and, like any other code you write,
takes time both to create and to maintain.
That’s all time not spent on adding functionality to applications.
Write all (and only) the mocking code you need.

### 4.2.2 Only mock objects with logic.

Even though a mock object may only return a value, 
its purpose is to replace something in your application that has logic in it.

### 4.2.3 Don’t mock objects that only carry values.

Objects used purely to transmit data (what are called “value objects” or “data transfer objects”) 
can be created in your tests using the original classes.

### 4.2.4 Be clear what you want the test to prove.

It’s not unusual to have a CUT(“code under test”) that calls an object, which calls another object, 
which then calls yet another object. If you’re creating a unit test, 
then you probably want to mock the first object in the chain: 
Mocking the first object isolates the CUT, which is your typical goal in a unit test.

### 4.2.5 Keep your mocks simple

If all you need is to have the mock return a value or a configured object, 
just have your mock object do that. 
If you need a mock object to report on what was passed to it, 
then configure the mock do just that.

If you start having your mock object do more—if, for example, 
you start writing code that mimics real business logic 
(what’s sometimes called a “simulation”) 
—then you’re missing the point of a mock object. 
You’re now not only increasing the amount of code that you’ll have to maintain 
and modify as your application evolves, you’re also adding to the logic associated with your application. 
And that raises a key question:

How, exactly, do you intend to test the logic inside a mock object?

When it comes to creating a mock, Prefer declarative code. 
Ideally, your mocking tool will let you generate mocks by stating what you want your mock to do 
and then let the mocking framework take care of both creating and invoking the mock. 
You want to avoid mocking tools that require any sort of logic to create or use a mock—that’s 
just more code that you’ll need (somehow) to test.

___
# 5. Best practice Java

## 5.1. Using Naming Conventions

An identifier (a class, a method, and a variable) should have the following characteristics:

**Self-explanatory:** a name must reveal its intention so everyone can understand and change the code easily. 
For example, the names `dor` `str` do not reveal anything. 
However, the names `daysToExpire` or `inputText` do reveal their intention clearly. 
Note that if a name requires a comment to describe itself, then the name is not self-explanatory.

**Meaningful distinctions:** If names must be different, then they should also mean something different. 
For example, the names `a1` and `a2` are meaningless distinction. 
The names `source` and `destination` are meaningful distinction.

**Pronounceable:** names should be pronounceable as naturally 
as spoken language because we are humans - very good at words. 
For example, which name can you pronounce and remember easily: `genStamp` or `generationTimestamp`?

**Here are some general naming rules:**

1. Class and interface names should be nouns, starting with an uppercase letter. 
For example: `Student`, `Car`, `Rectangle`, `Painter`, etc.

2. Variable names should be nouns, starting with a lowercase letter. 
For example: `number`, `counter`, `birthday`, `gender`, etc.

3. Method names should be verbs, starting with a lowercase letter. 
For example: `run`, `start`, `stop`, `execute`, etc.

4. Constant names should have all UPPERCASE letters and words are separated by underscores. 
For example: `MAX_SIZE`, `MIN_WIDTH`, `MIN_HEIGHT`, etc.

5. Using camelCase notation for names. 
For example: `StudentManager`, `CarController`, `numberOfStudents`, `runAnalysis`, etc.

A good reference for naming conventions is an Oracle’s publication: 
[Java Code Conventions](https://www.oracle.com/technetwork/java/codeconventions-150003.pdf) . 
You can consult this document to build your own naming conventions.

## 5.2 Ordering Class Members by Scopes

The best practice to organize member variables of a class by their scopes from most restrictive to least restrictive. That means we should sort the members by the visibility of the access modifiers: private, default (package), protected, and public. And each group separated by a blank line.

For example, the following members declaration looks quite messy:
```java
public class StudentManager {
    protected List<Student> listStudents;
    public int numberOfStudents;
    private String errorMessage;
    float rowHeight;
    float columnWidth;
    protected String[] columnNames;
    private int numberOfRows;
    private int numberOfColumns;
    public String title;
}
```

According to this best practice, the member declaration above should be sorted out like this:
```java
public class StudentManager {
    private String errorMessage;
    private int numberOfColumns;
    private int numberOfRows;

    float columnWidth;
    float rowHeight;
 
    protected String[] columnNames;
    protected List<Student> listStudents;
 
    public int numberOfStudents;
    public String title;
}
```

And the members in each group are sorted by alphabetic order. 
This private-first and public-last style helps us quickly locate member variables when the list grows up over times.

## 5.3 Class Members should be private

According to Joshua Bloch (author of Effective Java), we should minimize the accessibility of class members (fields) as inaccessible as possible. That means we should use the lowest possible access modifier (hence the private modifier) to protect the fields. This practice is recommended in order to enforce information hiding or encapsulation in software design.

Consider the following class whose fields are made public:
```java
public class Student {
    public String name;
    public int age;
}
```
The problem with this poor design is that anyone can change the values of the fields inappropriately. For example:
```java
Student student = new Student();
student.name = "";
student.age = 2005;
```

Of course, we don’t want the name to be empty and the age to be unrealistic. 
So this practice encourages us to hide the fields and allow the outside code to change them
the through setter methods (or mutators). Here’s an example of a better design:
```java
public class Student {

    private String name;
    private int age;
 
    public void setName(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Name is invalid");
        }
 
        this.name = name;
    }
 
    public void setAge(int age) {
        if (age < 1 || age > 100) {
            throw new IllegalArgumentException("Age is invalid");
        }
 
        this.age = age;
    }
}
```
As you can see, the fields name and age are declared to be private so the outside code cannot change 
them directly (information hiding). And we provide two setter methods `setName()` and `setAge()` 
which always check for valid arguments before actually updating the fields. 
This ensures the fields always get appropriate values.

## 5.4 Using Underscores in Numeric Literals

This little update from Java 7 helps us write lengthy numeric literals much more readable. 

Consider the following declaration:
```java
int maxUploadSize = 20971520;
long accountBalance = 1000000000000L;
float pi = 3.141592653589F;
```

And compare with this one:
```java
int maxUploadSize = 20_971_520;
long accountBalance = 1_000_000_000_000L;
float pi = 3.141_592_653_589F;
```
Which is more readable?

So remember to use underscores in numeric literals to improve readability of your code.

## 5.5 Avoid Empty Catch Blocks

It’s a very bad habit to leave catch blocks empty, 
as when the exception is caught by the empty catch block, 
the program fails in silence, which makes debugging harder. 
Consider the following program which calculates sum of two numbers from command-line arguments:
```java
public class Sum {
    public static void main(String[] args) {
        int a = 0;
        int b = 0;

        try {
            a = Integer.parseInt(args[0]);
            b = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
        }
 
        int sum = a + b;
 
        System.out.println("Sum = " + sum);
    }
}
```
Note that the catch block is empty. 
If we run this program by the following command line:

```java
java Sum 123 456x
```

It will fail silently:
```java
Sum = 123
```

It’s because the second argument `456y` causes a `NumberFormatException` to be thrown, but there’s no handling code 
in the catch block so the program continues with incorrect result.
Therefore, the best practice is to avoid empty catch blocks. 

Generally, we should do the following things when catching an exception:
1. Inform the user about the exception, e.g. tell them to re-enter inputs or show an error message.
2. Log the exception using JDK Logging or Log4J.
3. Wrap and re-throw the exception under a new exception.

Depending on the nature of the program, the code for handling exception may vary. But the rule of thumb is never “eat” an exception by an empty catch block.

Here’s a better version of the program above:
```java
public class Sum {
    public static void main(String[] args) {
        int a = 0;
        int b = 0;

        try {
            a = Integer.parseInt(args[0]);
            b = Integer.parseInt(args[1]);
 
        } catch (NumberFormatException ex) {
            System.out.println("One of the arguments are not number." +
                               "Program exits.");
            return;
        }
 
        int sum = a + b;
 
        System.out.println("Sum = " + sum);
    }
}

```
 
## 5.6 Using StringBuilder or StringBuffer instead of String Concatenation

In Java, we use the `+` operator to join Strings together like this:
```java
public String createTitle(int gender, String name) {
    String title = "Dear ";
 
    if (gender == 0) {
        title += "Mr";
    } else {
        title += "Mrs";
    }
 
    return title;
}
```
This is perfectly fine since only few String objects involved.

However, with code that involves in concatenating many Strings such as building a complex SQL statements 
or generating lengthy HTML text, the `+` operator becomes inefficient as the Java compiler creates many 
intermediate String objects during the concatenation process.

Therefore, the best practice recommends using `StringBuilder` or `StringBuffer` to replace the `+` operator 
for concatenating many String objects together as they modify a String without creating intermediate String objects. 
`StringBuilder` is a non-thread safe and `StringBuffer` is a thread-safe version.

For example, consider the following code snippet that uses the `+` operator to build a SQL query:
```java
String sql = "Insert Into Users (name, email, pass, address)";
sql += " values ('" + user.getName();
sql += "', '" + user.getEmail();
sql += "', '" + user.getPass();
sql += "', '" + user.getAddress();
sql += "')";
```
With `StringBuilder`, we can re-write the above code like this:
```java
StringBuilder sbSql 
        = new StringBuilder("Insert Into Users (name, email, pass, address)");
 
sbSql.append(" values ('").append(user.getName());
sbSql.append("', '").append(user.getEmail());
sbSql.append("', '").append(user.getPass());
sbSql.append("', '").append(user.getAddress());
sbSql.append("')");
 
String sql = sbSql.toString();
```

## 5.7 Using Enums or Constant Class instead of Constant Interface

It’s a very bad idea to create an interface which is solely for declaring some constants without any methods. Here’s such an interface:
```java
public interface Color {
    public static final int RED = 0xff0000;
    public static final int BLACK = 0x000000;
    public static final int WHITE = 0xffffff;
}
```
It’s because the purpose of interfaces is for inheritance and polymorphism, not for static stuffs like that. So the best practice recommends us to use an enum instead. For example:
```java
public enum Color {
    BLACK, WHITE, RED
}
```
In case the color code does matter, we can update the enum like this:
```java
public enum Color {
 
    BLACK(0x000000),
    WHITE(0xffffff),
    RED(0xff0000);
 
    private int code;
 
    Color(int code) {
        this.code = code;
    }
 
    public int getCode() {
        return this.code;
    }
}
```
In a complex project, we can have a class which is dedicated to define constants for the application. For example:
```java
public class AppConstants {
    public static final String TITLE = "Application Name";
 
    public static final int VERSION_MAJOR = 2;
    public static final int VERSION_MINOR = 4;
 
    public static final int THREAD_POOL_SIZE = 10;
 
    public static final int MAX_DB_CONNECTIONS = 50;
 
    public static final String ERROR_DIALOG_TITLE = "Error";
    public static final String WARNING_DIALOG_TITLE = "Warning";
    public static final String INFO_DIALOG_TITLE = "Information";
}
```
So the rule of thumb is: Do not use interfaces for constants, use enums or dedicated classes instead.

## 5.8 Avoid Redundant Initialization (0-false-null)

It’s very unnecessary to initialize member variables to the following values: `0`, `false` and `null`. 
Because these values are the default initialization values of member variables in Java.

For example, the following initialization in declaration is unnecessary:
```java
public class Person {
    private String name = null;
    private int age = 0;
    private boolean isGenius = false;
}
```
This is also redundant:
```java
public class Person {
    private String name;
    private int age;
    private boolean;
 
    public Person() {
        String name = null;
        int age = 0;
        boolean isGenius = false;
    }
}
```
Therefore, if you know the default initialization values of member variables, 
you will avoid unnecessary explicit initialization.

## 5.9 Using Interface References to Collections

When declaring collection objects, references to the objects should be as generic as possible. This is to maximize the flexibility and protect the code from possible changes in the underlying collection implementations class. That means we should declare collection objects using their interfaces `List`, `Set`, `Map`, `Queue` and `Deque`.

For example, the following class shows a bad usage of collection references:
```java
public class CollectionsRef {
 
    private HashSet<Integer> numbers;
 
    public ArrayList<String> getList() {
        return new ArrayList<String>();
    }
 
    public void setNumbers(HashSet<Integer> numbers) {
        this.numbers = numbers;
    }
}
```
Look at the reference types which are collection implementation classes - this locks the code to work with only these classes `HashSet` and `ArrayList`. What if we want the method `getList()` can return a `LinkedList` and the method `setNumbers()` can accept a `TreeSet`?

The above class can be improved by replace the class references to interface references like this:
```java
public class CollectionsRef {
 
    private Set<Integer> numbers;
 
    public List<String> getList() {
        // can return any kind of List
        return new ArrayList<String>();
    }
 
    public void setNumbers(Set<Integer> numbers) {
        // can accept any kind of Set
        this.numbers = numbers;
    }
}
```
 
## 5.10 Avoid using for loops with indexes

Don’t use a for loop with an index (or counter) variable if you can replace it with the enhanced for loop (since Java 5)
or forEach (since Java 8). It’s because the index variable is error-prone, 
as we may alter it incidentally in the loop’s body, or we may start the index from 1 instead of 0.

Consider the following example that iterates over an array of Strings:
```java
String[] names = {"Alice", "Bob", "Carol", "David", "Eric", "Frank"};
 
for (int i = 0; i < names.length; i++) {
    doSomething(names[i]);
}
```
As you can see, the index variable i in this for loop can be altered incidentally which may cause unexpected result. We can avoid potential problems by using an enhanced for loop like this:
```java
for (String aName : names) {
    doSomething(aName);
}
```
This does not only remove potential issues but also make the code cleaner and more succinct.




 


