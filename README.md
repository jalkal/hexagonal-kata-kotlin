# hexagonal-kata-kotlin

The idea of this kata is to learn how to implement a hexagonal architecture project while understanding some benefits that it will give to you and your team.

## Table of Contents

- [hexagonal-kata-kotlin](#hexagonal-kata-kotlin)
    * [Assumptions (TODO)](#assumptions--todo-)
    * [Benefits (TODO)](#benefits--todo-)
    * [Rules (TODO)](#rules--todo-)
    * [Components (TODO)](#components--todo-)
    * [Kata Structure](#kata-structure)
        + [1. Introduction](#1-introduction)
        + [2. First Changes](#2-first-changes)
        + [3. More changes on discounts](#3-more-changes-on-discounts)
        + [4. New External Services version](#4-new-external-services-version)
        + [5. That's all folks](#5-that-s-all-folks)
        + [6. Hexagonal architecture to the rescue](#6-hexagonal-architecture-to-the-rescue)

## Assumptions (TODO)

Just practising hexagonal, nothing about TDD or other practices

## Benefits (TODO)

There are multiple explanations out there about the benefits of hexagonal architecture that you can read to understand them, but as a summary, I would say:

- Infrastructure independence
- Business logic isolation
- Easy to test
- High Maintainability

## Rules (TODO)

- never use something from outside (app does not use service)

## Components (TODO)

- Domain Model
- Ports
- Adapters

## Kata Structure

### 1. Introduction

Your new job starts today in a very famous media group. You have been told that they want a new application to provide data to a new platform they are starting
to develop. They will need account, product and invoice information, and the last intern created a simple application that accomplishes what they want nowadays.
Anyway, you have been told, that you will need to add new features to it quite soon, so it's better to start taking a look at it.

#### 1.a Open the `hexagonal-kata-kotlin-application` module

You will see a simple Ktor application (Ktor & Koin are not relevant to the kata itself) with 3 REST endpoints: `getAccountByAccountId`
, `getProductsByAccountId` and `getInvoiceByAccountId`. These endpoints call an `AccountApplication`, and this class is the one that retrieves account and
product information from the company currently services.

#### 1.b Execute `AccountControllerTest` to check that everything works

There is an `AccountControllerTest` class that checks that everything works as expected. You can execute it to see that everything is green, working and
fantastic. Take a look at the rest of the application and be sure that you understand every bit of it before continuing.

### 2. First Changes

#### 2.a Update the external services dependency to the new version

Seems that since the last intern left the company, has been a lot of changes in the company servers, and our current application is a little outdated.

Just open your pom.xml file and update the `<external.services.version>1</external.services.version>` to use version `2`. Automatically you will realise that
these new external services are breaking your current implementation. Make your AccountApplication compile again.

#### 2.b Update now the json returned from your controller

Seems that changing just the data providers is not enough. Now that you have new data, your consumers also want to know about it. Seems that they have all their
work done in their side, so your manager sends you the JSON format you'll need to return.

_Copy all files from `/kata-test-data/2.b/results` folder to your `test/resources` folder and overwrite the existing files. Execute the tests and try to modify
your code to accomplish this new version of the tests._

#### 2.c Your premium accounts have 'special' discounts. Apply them

This new version of the external services returns more data. Account data has a special field named premiumAccount, that will let you know who is eligible for a
special discount. Apply those premium prices to all the accounts.

_Copy all files from `/kata-test-data/2.c/results` folder to your `test/resources` folder and overwrite the existing files. Execute the tests and try to modify
your code to accomplish this new version of the tests._

### 3. More changes in discounts

#### 3.a New Discounts for your premium accounts

There is a new feature to be applied to your application. From now on, all the premium accounts that have a product of each type (types 1xx, 2xx, 3xx, 4xx) will
have another discount added to the existing current premium price. You'll need to discount an additional 15% on each product, resulting in a 15% total invoice
discount.

_Copy all files from `/kata-test-data/3.a/results` folder to your `test/resources` folder and overwrite the existing files. Execute the tests and try to modify
your code to accomplish this new version of the tests._

### 4. New External Services version

#### 4.a Update external-services version

You need to update external services to version `3`. This one brings some upgrades to the products. From now on, `ProductsRepository` has been removed, and all
the product information has been moved to the new `ProductsClient`.

Just update the version and make all tests green again

### 5. That's all folks

Now, after all these changes, let's think about it. All of them has been really easy (it's not the objective of the kata)
. The main point you should realise is that just two of these changes are changes to your business rules. Have you changed your application more than just 2
times to add these new features? Then, you are doing something wrong

The main idea is that, if you need to add any change, but is not a business change, your `Application` or business rules, should not be modified. You don't need
to change anything in those classes. It's quite a logic thing.

External changes are external changes, so they should be treated outside your business logic, regardless if they affect the clients/repositories you consume
from or the rest of the controllers / other services you offer. So let's try to apply some hexagonal architecture here and see if that improves our code.

### 6. Hexagonal architecture to the rescue

#### 6.a Revert or stash your changes until now

Just revert or stash your changes until now, and start again from scratch. We are now going to follow the same steps, but with a nice hexagonal architecture
module. So, just check you have your initial code again and all the tests are green.

Take your time to remember how was the application in the first iteration. As you already know how the application is, we'll go faster.

#### 6.b Create Hexagonal architecture package structure

First, we need to create the package structure for our hexagonal architecture. I usually model everything inside an `api` package, but there are other options (
model, domain, and thousands more...). Let's create the `api` package.

In that `api` package, we want to model both `external` ports (_primary_, _outbound_) and `internal` ports (_secondary_, _inbound_), so create also that
packages (`api/external` and `api/internal`)

In these `external/internal` folders, we want to create also a `model` one (to store the models our ports will offer/use) and a `port` one (to store the
ports/interfaces that we'll offer to our consumers/application)

You should have something similar to this:

- api/external/model
- api/external/port
- api/internal/model
- api/internal/port

#### 6.c Lets create our first external port

As an external port, we want to model the services we'll offer to our consumers, so in this case, we'll need a `getAccount`, a `getProducts` and a
`getInvoice` functions. Each of them receives an `AccountId`, and they return different models. I would propose have an interface (call it AccountApi) with the
following contract:

```
    fun getAccount(accountId: AccountId) : Account
    fun getProducts(accountId: AccountId) : List<Product>
    fun getInvoice(accountId: AccountId) : Invoice
```

Then, you will need to create, in the model folder, each returning class. At the moment, just use the fields that you need to accomplish your test contract. Try
to use correct types for each field, for example, price should be a BigDecimal, or even better, something called Money (anyway, this is again not something part
of this kata, so you can use BigDecimal perfectly).

We just created our first external-port! That was easy!

#### 6.d Continue now with our internal ports

Now that we have our external port well-defined, would be great to define also the internal ports that our application will need. In this case, as we already
know what the application will do and which data we required to accomplish it, it will be easy. In other cases, you will need to analyse your requirements and
design something beforehand.

So, what does our application need to retrieve to fulfil its external contract? Of course, account/account information and product information. So let's create
these internal ports.

In the internal package, create two different interfaces, one for each type/group of information you want to deal with,
`AccountApi` and `ProductApi` (note that `AccountApi` has the same name as the external API, but different packages)

In the `AccountApi` interface, you just need a function to return all the Account information. In the `ProductApi`, you need also just one function, to retrieve
the products of an account (with all the product information).

```
    fun getAccount(accountId: AccountId) : Account
    fun getProducts(accountId: AccountId) : List<Product>
```

You'll need also to create the models you use in your interfaces in the internal/model folder, `AccountId`, `Account`
and `Product`. In this case, as you can see, the models in your internal folder are exactly the same as the models on the external folder. So we can create them
all together in a model folder outside external/internal folders. This is something controversial because your input model could be different from the output
model, even having the same fields. I'll explain this point [later on](#different-external-internal-model).

So now, you api folder structure should look like this:

- api/model (Model.kt)
- api/external/port (AccountApi)
- api/internal/port (AccountApi, ProductApi)

#### 6.e With our internal ports in place, we can implement our business logic (external port)

Yes, we don't really need to know how these internal ports will retrieve the data, neither where they retrieve it from, we just know that they will offer us
everything we need. So, having this in mind, we can start to create our business logic to accomplish the tests we have on our controller.

Of course, you won't be able to test it because at the moment there is no implementation for our ports, but we can, for example, create some nice unit tests of
the whole application, testing all our business logic, without external components.

We need a class that implements our external port, and this class will also receive as parameters the two internal ports we have just created.

```
    class AccountApplication(private val accountApi: InternalAccountApi, private val productApi: ProductApi) : ExternalAccountApi
```

As you can see, I played with the import alias to distinguish our external and internal ports. Then, just implement the functions you need to accomplish
your `ExternalAccountApi` contract.

It should be as easy as call your `InternalAccountApi`, and do some minor logic in the `getInvoice` function. If you did not use TDD to do it, then you'll need
to do some tests, but they should be quite easy (just return what the internal API returns, and the minor logic on the getInvoice)

_Voilà_, you have your business logic done, with tests, and no external dependencies (check that you don't have any import that is not `api.model`
, `api. external` or `api.internal` packages). If you have any external package, you've made something wrong, and we'll be using directly something from an
external component, so review it!

#### 6.f Internal ports implementations

Now that you have our external port implemented, we can do the same with our internal ports. As you will see, its also quite easy. Let's start with the
AccountApi one. We need a class that implements that AccountApi. We can call that class, AccountAdapter. And to accomplish our AccountApi contract, it will need
some external dependency, in this case, the CustomerClient. Create it, passing a CustomerClient instance, and map the CustomerClient response to the object you
want to return from our internal port, AccountApi.

Where did you put that class? Think about it, where should it live? API, domain, infrastructure?

Of course, it's an infrastructure class.

We can do the same for the ProductApi / ProductAdapter. As you can see here, we need two different external dependencies, ProductClient and ProductRepository,
and we'll need to do some logic to be able to accomplish our ProductApi contract. So let's do it!

Now that we have both internal APIs implementations ready, you can do some tests (if you did not) to check that it works as expected. Remember that you will
need to mock the external dependencies.

#### 6.g Make the project green again

Having everything ready, you can solve the compilation problems on the controller and Application.kt file. If you are not familiar with Koin or any other
DependencyInjection framework, I'll give you the solution below:

```
    install(Koin) {
        modules(module {
            single { AccountClient(AccountClientConfiguration()) }
            single { ProductClient(ProductClientConfiguration()) }
            single { ProductRepository(ProductRepositoryConfiguration()) }

            single { AccountAdapter(accountClient = get()) }
            single { ProductAdapter(productClient = get(), productRepository = get()) }

            single { AccountApplication(accountApi = get<AccountAdapter>(), productApi = get<ProductAdapter>()) }
            single { AccountController(accountApi = get<AccountApplication>()) }
        })
    }
```

As you can see, `AccountApplication` and `AccountController` need some help on the beans they should inject. Why? We need to specify to Koin that we are going
to use the implementation of our ports. As you can see, AccountAdapter and ProductAdapter and the implementation of our internal ports, while AccountApplication
is the implementation of our external port.

Now, you just need to make the controller tests green again. To do it, there are many ways, but I recommend you creating inner classes inside the controller
class and made them accomplish the controller specification. Why? because this kata is to see the benefits of the hexagonal architecture, not to explore the
amazing possibilities of Jackson, neither to create your own JSON serializer. So do it easy and simple for this kata.

Execute your Controller tests and see them all green! We're done, we have our application following a great hexagonal architecture!

### 7. Hexagonal Architecture, and what now?

That's what you can think, what now? We have an Application class more or less similar to the one we already had without hexagonal, but we also have multiple
classes more. This is a prepared kata, but someone can even call it class explosion...

Let's think what are the main benefits on this exercise. The first and most important one that you will realise quite soon (if not already) is that we aren't
depending on any external dependency to execute our business logic. This means that you can completely design, implement and test it without any external
interference (_Business logic isolation_).

This also means, that any change in any external dependency, will not affect your business rule. If we change a repository by a client, or any other
infrastructure change, should not affect your business logic (_Infrastructure independence_).

You can also test every part isolated from the others. Talking about unit tests, you just need 3 different layers of unit tests, Controller or Consumers,
business logic and adapters (_Easy to test_).

Also, as you will see in the next point, maitaining the code is quite easy, because everything is separated and there is no coupling between business and
infrastructure (_High Maintainability_).

There's also something quite interesting in this kata, that is the ProductAdapter. If you remember the initial implementation, we had both ProductClient and
ProductRepository. But when we applied the hexagonal concepts, we just left one ProductAdapter. That's really important, because as exaplined before, we don't
mind where the data come from, we just know that we're gonna use it. In our case, we call just one Api (or adapter) but the adapter itself needs to call many
different infrastructures to accomplish its own contract, in this case, the client and the repository.

### 8. Lets continue with the exercise

So, now that we have everything modeled correctly with a new and shiny hexagonal architecture, let's try to do the exercise again

#### 8.a Update to the external services version 2

Same as before, update the version to 2 and make everything compile again. You will see that your modifications should not imply the business logic, as there is
no change there. Just modify adapters and controller and ready to test again.

#### 8.b Premium discounts

You need now to apply the premium discounts (_same as we made before_). We'll need to make some choices here. First, we can continue using the Product model
that we have until now (the same for external and internal model) but that would be a little nasty. Why? because we need to override the price with the premium
one in soma cases. So would be nice to have a Product model coming from our internal model that gives us all the data that we want, and another one for our
external model that returns to our consumers that already processed Product information.

Let's make that change. It will affect our contracts, because we are going to modify our returning object in the external port, but sometimes is needed.
Remember to move the existing/new models to the correct package (in the case they are just internal or external models)




--------------------------------

#### 2.c Your premium accounts have 'special' discounts. Apply them

This new version of the external services returns more data. Account data has a special field named premiumAccount, that will let you know who is eligible for a
special discount. Apply those premium prices to all the accounts.

_Copy all files from `/kata-test-data/2.c/results` folder to your `test/resources` folder and overwrite the existing files. Execute the tests and try to modify
your code to accomplish this new version of the tests._

### 3. More changes in discounts

#### 3.a New Discounts for your premium accounts

There is a new feature to be applied to your application. From now on, all the premium accounts that have a product of each type (types 1xx, 2xx, 3xx, 4xx) will
have another discount added to the existing current premium price. You'll need to discount an additional 15% on each product, resulting in a 15% total invoice
discount.

_Copy all files from `/kata-test-data/3.a/results` folder to your `test/resources` folder and overwrite the existing files. Execute the tests and try to modify
your code to accomplish this new version of the tests._

### 4. New External Services version

#### 4.a Update external-services version

You need to update external services to version `3`. This one brings some upgrades to the products. From now on, `ProductsRepository` has been removed, and all
the product information has been moved to the new `ProductsClient`.

Just update the version and make all tests green again
