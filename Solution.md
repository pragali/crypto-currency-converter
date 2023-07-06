A JAVA web application to fetch the current localized price of a cryptocurrency.

#Assumptions
Have used the following two free apis

1) for loading IP address https://ipapi.co/
This provides me the country code and languages

Using this I calculated unformatted price and fiat code using Numberformat.getCurrencyInstance()

2) for getting crypto currency fiat : https://pro-api.coinmarketcap.com/v2/tools/price-conversion
The above unformatted price and fiat has been passed to above to get the conversion.

Application landing page is http://localhost:8080/convert
Optionally you can use URL with params for ex http://localhost:8080/convert?ipAddress=208.67.222.222&coinSymbol=BTC