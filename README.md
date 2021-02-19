# hexagonal-kata-kotlin

The idea of this kata is to learn how to implement a hexagonal architecture project while understanding some benefits
that it will give to you and your team.

## Benefits

There are multiple explanations out there about the benefits of hexagonal architecture that you can read to understand
them, but as a summary I would say:

- Infrastructure independence,
- Business logic isolation
- Easy to test
- High Maintainability

## Components

- Domain Model
- Ports
- Adapters

## Kata Structure

### 1. Introduction

Your new job starts today in a very famous media group. You have been told that they want a new application to provide
data to a new platform they are starting to develop. They will need customer, product and invoice information, and the
last intern created a simple application that accomplish what they want nowadays. Anyway, you have been told, that you
will need to add new features to it quite soon, so it's better to start taking a look at it.

#### 1.a **Open the `hexagonal-kata-kotlin-application` module**.

You will see a simple ktor application (ktor & koin are not relevant to the kata itself) with 3 REST
endpoints: `getCustomerByAccountId`, `getProductsByAccountId` and
`getInvoiceByAccountId`. These endpoints call a `CustomerApplication`, and this class is the one that retrieves customer
and product information from the company current services.

#### 1.b **Execute `CustomerControllerTest` to check that everything works**.

There is a `CustomerControllerTest` class that checks that everything works as expected. You can execute it to see that
everything is green, working and fantastic. Take a look at the rest of the application and be sure that you understand
every bit of it before continuing.

### 2. First Changes

#### 2.a **Update the external services dependency to the new version**

Seems that since the last intern left the company, has been a lot of changes in the company servers, and our current
application is a little outdated.

Just open your pom.xml file and update the `<external.services.version>1</external.services.version>` to use version `2`
. Automatically you will realise that these new external services are breaking your current implementation. Make your
CustomerApplication compile again.

#### 2.b **Update now the json returned from your controller**

Seems that changing just the data providers is not enough. Now that you have new data, your consumers also want to know
about it. Seems that they have all their work done in their side, so your manager sends you the json format you'll need
to return.

_Copy all files from `/kata-test-data/2.b/results` folder to your `test/resources` folder and overwrite the existing files.
Execute the tests and try to modify your code to accomplish this new version of the tests._

#### 2.c **Your premium customers have 'special' discounts. Apply them**

This new version of the external services returns more data. Customer data, has a special field named premiumCustomer,
that will let you know who is eligible for a special discount. Apply those premium prices to all the customers.

_Copy all files from `/kata-test-data/2.c/results` folder to your `test/resources` folder and overwrite the existing files.
Execute the tests and try to modify your code to accomplish this new version of the tests._

### 3. More changes on discounts

#### 3.a **New Discounts for your premium customers**

There is a new feature to be applied to your application. From now on, all the premium customers that has a product of
each type (types 1xx, 2xx, 3xx, 4xx) will have another discount added to the existing current premium price. You'll need
to discount an additional 15% in each product, resulting in a 15% total invoice discount.

_Copy all files from `/kata-test-data/3.a/results` folder to your `test/resources` folder and overwrite the existing files.
Execute the tests and try to modify your code to accomplish this new version of the tests._

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

External changes are external changes, so they should be treated outside of your business logic, regardless if they
affect the clients/repositories you consume from, or the rest controllers / other services you offer. So let's try 
to apply some hexagonal architecture here and see if that improves something our code.

### 6. Hexagonal architecture to the rescue

#### 6.a **Revert or stash your changes until now**

Just rever or stash your changes until now, and start again from scratch. We are now going to follow the same steps, 
but with a nice hexagonal architecture module. So, just check you have your initial code again and all the tests are 
green.



