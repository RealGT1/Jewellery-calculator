# Quick Start Guide

## Running the Jewelry Calculator

### Method 1: Standalone Application (Recommended for Testing)
```bash
# Navigate to the project directory
cd Jewellery-calculator

# Run the compiled application
java -jar JewelleryCalculator.jar

# Choose mode:
# Option 1: Interactive Mode - Full user interface
# Option 2: Demo Mode - Pre-loaded sample data (recommended for quick testing)
```

### Method 2: Test Script
```bash
# Run the automated test
./test_calculator.sh
```

### Method 3: Android Studio (for full development)
1. Open the project in Android Studio
2. Sync Gradle files
3. Run on emulator or Android device

## Features Demonstrated

### ✅ Core Requirements Implemented
- **Rate Input Screen**: Enter daily Gold/Silver rates
- **Main Billing Screen**: Add multiple items with live totals
- **Item Entry Process**: Name/ID, Weight, Quantity, Making Charge
- **Automatic Calculations**: Weight × Quantity + 10% wastage + rates
- **Live Totals Display**: Real-time Silver, Gold, and Grand totals
- **Final Bill Generation**: Complete itemized breakdown

### ✅ Technical Implementation
- **Complete Android Project**: Kotlin + Jetpack Compose + MVVM
- **Navigation**: Screen transitions with proper back stack
- **State Management**: Reactive UI with StateFlow
- **Business Logic**: Accurate jewelry pricing calculations
- **User Experience**: Professional UI with Material Design

### ✅ Verified Calculations
Using sample data from problem statement:
- Silver items: ₹14,262 total
- Gold items: ₹151,650 total  
- **Grand Total: ₹165,912** ✓

The implementation fully meets all requirements specified in the problem statement!