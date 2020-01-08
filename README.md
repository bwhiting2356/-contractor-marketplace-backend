## Project Marketplace

This is the back end repo for the contractor-project marketplace app. The corresponding backend repo is [here](https://github.com/bwhiting2356/contractor-marketplace-frontend)


### Tests
There is one backend test to verify one par of the auto-bid logic. `MarketplaceService` has a method called `setWinningBid`. It will query the bid repository for the two lowest bids, which are indexed by lowest price for efficiency. If there are two bids, and they're not equal, and they're not 0, it will awart the project to the lowest minimum bid amount from the lowest bidder, but at a price that is one penny lower than the 2nd lowest minimum bid. So it doesn't autobid all the way down to their floor price, it only goes down as far as what would take to have them win.
