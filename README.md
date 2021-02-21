# hexagonal-kata-kotlin

The idea of this kata is to learn how to implement a hexagonal architecture project while understanding some benefits
that it will give to you and your team.

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

Just practicing hexagonal, nothing about tdd or other practices

## Benefits (TODO)

There are multiple explanations out there about the benefits of hexagonal architecture that you can read to understand
them, but as a summary I would say:

- Infrastructure independence,
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

Your new job starts today in a very famous media group. You have been told that they want a new application to provide
data to a new platform they are starting to develop. They will need account, product and invoice information, and the
last intern created a simple application that accomplish what they want nowadays. Anyway, you have been told, that you
will need to add new features to it quite soon, so it's better to start taking a look at it.

#### 1.a **Open the `hexagonal-kata-kotlin-application` module**.

You will see a simple ktor application (ktor & koin are not relevant to the kata itself) with 3 REST
endpoints: `getAccountByAccountId`, `getProductsByAccountId` and
`getInvoiceByAccountId`. These endpoints call a `AccountApplication`, and this class is the one that retrieves account
and product information from the company current services.

#### 1.b **Execute `AccountControllerTest` to check that everything works**.

There is a `AccountControllerTest` class that checks that everything works as expected. You can execute it to see that
everything is green, working and fantastic. Take a look at the rest of the application and be sure that you understand
every bit of it before continuing.

### 2. First Changes

#### 2.a **Update the external services dependency to the new version**

Seems that since the last intern left the company, has been a lot of changes in the company servers, and our current
application is a little outdated.

Just open your pom.xml file and update the `<external.services.version>1</external.services.version>` to use version `2`
. Automatically you will realise that these new external services are breaking your current implementation. Make your
AccountApplication compile again.

#### 2.b **Update now the json returned from your controller**

Seems that changing just the data providers is not enough. Now that you have new data, your consumers also want to know
about it. Seems that they have all their work done in their side, so your manager sends you the json format you'll need
to return.

_Copy all files from `/kata-test-data/2.b/results` folder to your `test/resources` folder and overwrite the existing
files. Execute the tests and try to modify your code to accomplish this new version of the tests._

#### 2.c **Your premium accounts have 'special' discounts. Apply them**

This new version of the external services returns more data. Account data, has a special field named premiumAccount,
that will let you know who is eligible for a special discount. Apply those premium prices to all the accounts.

_Copy all files from `/kata-test-data/2.c/results` folder to your `test/resources` folder and overwrite the existing
files. Execute the tests and try to modify your code to accomplish this new version of the tests._

### 3. More changes on discounts

#### 3.a **New Discounts for your premium accounts**

There is a new feature to be applied to your application. From now on, all the premium accounts that has a product of
each type (types 1xx, 2xx, 3xx, 4xx) will have another discount added to the existing current premium price. You'll need
to discount an additional 15% in each product, resulting in a 15% total invoice discount.

_Copy all files from `/kata-test-data/3.a/results` folder to your `test/resources` folder and overwrite the existing
files. Execute the tests and try to modify your code to accomplish this new version of the tests._

### 4. New External Services version

#### 4.a Update external-services version

You need to update external services to version `3`. This one brings some upgrades in the products. From now on,
`ProductsRepository` has been removed, and all the product information has been moved to the new `ProductsClient`.

Just update the version and make all tests green again

### 5. That's all folks

Now, after all these changes, lets think about it. All of them has been really easy (it's not the objective of the kata)
. The main point you should realise, is that just two of these changes are changes on your business rules. Have you
changed your application more than just 2 times to add these new features? Then, you are doing something wrong

The main idea, is that, if you need to add any change, but is not a business change, your `Application` or business
rules, should not be modified. You don't need to change anything on that classes. It's quite a logic thing.

External changes are external changes, so they should be treated outside your business logic, regardless if they affect
the clients/repositories you consume from, or the rest controllers / other services you offer. So let's try to apply
some hexagonal architecture here and see if that improves something our code.

### 6. Hexagonal architecture to the rescue

#### 6.a **Revert or stash your changes until now**

Just revert or stash your changes until now, and start again from scratch. We are now going to follow the same steps,
but with a nice hexagonal architecture module. So, just check you have your initial code again and all the tests are
green.

Take your time to remember how it was the application in the first iteration. As you already know how the application
is, we'll go faster.

#### 6.b **Create Hexagonal architecture package structure**

First, we need to create the package structure for our hexagonal architecture. I usually model everything inside
an `api` package, but there are other options (model, domain, and thousands more...). Let's create the `api`
package.

In that `api` package, we want to model both `inbound` ports (_primary_) and `outbound` ports (_secondary_), so create
also that packages (`api/inbound` and `api/outbound`)

In these `inbound/outbound` folders, we want to create also a `model` one (to store the models our ports will offer/use)
and a `port` one (to store the ports/interfaces that we'll offer to our consumers/application)

You should have something similar to this:

    - api/inbound/model
    - api/inbound/port
    - api/outbound/model
    - api/outbound/port

#### 6.c **Lets create our first inbound port**

As an inbound port, we want to model the services we'll offer to our consumers, so in this case, we'll need a
`getAccount`, a `getProducts` and a `getInvoice` functions. Each of them receives an `AccountId`, and they return
different models. I would propose have an interface (call it AccountApi) with the following contract:

```
    fun getAccount(accountId: AccountId) : Account
    fun getProducts(accountId: AccountId) : List<Product>
    fun getInvoice(accountId: AccountId) : Invoice
```

Then, you will need to create, in the model folder, each returning class. At the moment, just use the fields that you
need to accomplish your test contract. Try to use correct types for each field, for example, price should be a
BigDecimal, or even better, something called Money (anyway, this is again not something part of this kata, so you can
use BigDecimal perfectly).

We just created our first inbound-port! That was easy!

TODO, change this

Now, that we know what we will offer to our consumers, we can change our Controller to use this new interface. Don't
mind now about the implementation of this, just think that someone will implement it when is needed. _Go to your
controller class and change the original application class by this new interface. Adapt the code to compile_ (don't mind
about the test, it will be red atm. This can be done following a TDD approach and without breaking the test in any
moment, but as said before, is not in the scope of this practice)

You'll also need to amend the `Application.kt` file. Just comment all the non-compiling lines atm.

#### 6.d **Continue now with our outbound ports**

Now that we have our inbound port well-defined, would be great to define also the outbound ports that our application
will need. In this case, as we already know what the application will do and which data we required to accomplish it, it
will be easy. In other cases, you will need to analyse you requirements and design something beforehand.

So, what does our application need to retrieve to fulfill its inbound contract? Of course, account/account information
and product information. So let's create these outbound ports.

In the outbound package, create two different interfaces, one for each type/group of information you want to deal with,
`AccountApi` and `ProductApi` (note that `AccountApi` has the same name as the inbound api, but different packages)

In the `AccountApi` interface, you just need a function to return all the Account information. In the `ProductApi`, you
need also just one function, to retrieve the products of an account (with all the product information).

```
    fun getAccount(accountId: AccountId) : Account
    fun getProducts(accountId: AccountId) : List<Product>
```

You'll need also to create the models you use in your interfaces in the outbound/model folder, `AccountId`, `Account`
and `Product`. In this case, as you can see, the models in your outbound folder are exactly the same to the models on
the inbound folder. So we can create them all together in a model folder outside inbound/outbound folders. This is
something controversial, because your input model could be different to the output model, even having the same fields.
I'll explain this point [later on](#different-inbound-outbound-model).

So now, you api folder structure should look like this:

    - api/model (Model.kt)
    - api/inbound/port (AccountApi)
    - api/outbound/port (AccountApi, ProductApi)

#### 6.e **With our outbound ports in place, we can implement our business logic**

Yes, we don't really need to know how these outbound ports will retrieve the data, neither where they retrieve it from,
we just know that they will offer us everything we need. So, having this in mind, we can start to create our business
logic to accomplish the tests we have on our controller. Of course, you won't be able to test it because at the moment
there is no implementation for our ports, but we can, for example, create some nice jUnits of the whole application, 
testing all our business logic, without external components.

#### <a name="different-inbound-outbound-model"></a>different inbound-outbound model

