# Jewellery Calculator - User Guide

## Overview

The Jewellery Calculator is an Android application designed for jewellery shops to create detailed bills for customers. It functions as a shopping cart system with automatic price calculations.

## Getting Started

### 1. Setting Daily Rates

When you first open the app, you'll see the **Rate Input Screen**.

- Enter the current day's **Gold Rate** (per gram)
- Enter the current day's **Silver Rate** (per gram)
- Tap **"Proceed to Billing"** to continue

**Example:**
- Gold Rate: 9100
- Silver Rate: 108

### 2. Adding Items

On the **Main Billing Screen**, you can add items to the bill:

#### Adding a Gold Item
1. Tap **"Add Gold Item"**
2. Fill in the item details:
   - **Item Name/ID**: e.g., "Ring", "g1"
   - **Weight**: Weight in grams
   - **Quantity**: Number of pieces
   - **Making Charge**: Additional charges for crafting
3. Tap **"Add Item"** to confirm

#### Adding a Silver Item
1. Tap **"Add Silver Item"**
2. Fill in the same details as above
3. Tap **"Add Item"** to confirm

### 3. Viewing Live Totals

As you add items, the app automatically calculates and displays:
- **Silver Total**: Sum of all silver items
- **Gold Total**: Sum of all gold items
- **Grand Total**: Combined total of the entire bill

### 4. Managing Items

- **View Items**: All added items appear in the list
- **Delete Items**: Tap the delete icon next to any item to remove it
- **Edit Items**: Currently, you need to delete and re-add items to modify them

### 5. Generating the Final Bill

1. Once you've added all items, tap **"Generate Bill"**
2. This opens the **Bill Summary Screen** with detailed calculations
3. Review the complete breakdown of all items
4. Tap **"New Bill"** to start fresh with new rates

## How Calculations Work

### Formula
For each item, the app calculates:

1. **Total Weight** = Weight × Quantity
2. **Wastage** = Total Weight × 10%
3. **Final Weight** = Total Weight + Wastage
4. **Metal Cost** = Final Weight × Metal Rate
5. **Total Cost** = Metal Cost + Making Charge

### Example Calculation

**Silver Item**: Ring, 10g, Quantity 2, Making Charge ₹200, Silver Rate ₹108/g

1. Total Weight = 10g × 2 = 20g
2. Wastage = 20g × 10% = 2g
3. Final Weight = 20g + 2g = 22g
4. Metal Cost = 22g × ₹108 = ₹2,376
5. Total Cost = ₹2,376 + ₹200 = ₹2,576

## Complete Workflow Example

### Step 1: Set Rates
- Gold Rate: ₹9,100/g
- Silver Rate: ₹108/g

### Step 2: Add Items
1. **Silver Item 1**
   - Name: s1
   - Weight: 10g
   - Quantity: 4
   - Making Charge: ₹250
   - *Calculated Total: ₹5,002*

2. **Silver Item 2**
   - Name: s2
   - Weight: 25g
   - Quantity: 3
   - Making Charge: ₹350
   - *Calculated Total: ₹9,260*

3. **Gold Item 1**
   - Name: g1
   - Weight: 15g
   - Quantity: 1
   - Making Charge: ₹1,500
   - *Calculated Total: ₹151,650*

### Step 3: View Live Totals
- Silver Total: ₹14,262
- Gold Total: ₹151,650
- Grand Total: ₹165,912

### Step 4: Generate Bill
Review the detailed breakdown with all calculations shown.

## Tips for Best Use

1. **Double-check rates** before proceeding to billing
2. **Use clear item names** for easy identification
3. **Review items** before generating the final bill
4. **Keep making charges realistic** based on your shop's pricing
5. **Start a new bill** for each customer

## Troubleshooting

### Common Issues

**Invalid Rate Error**
- Ensure rates are positive numbers
- Don't use letters or special characters
- Use decimal point (.) for fractional values

**Invalid Item Input**
- All fields must be filled
- Weight and quantity must be positive numbers
- Making charge can be zero but not negative

**Empty Bill**
- You must add at least one item before generating a bill
- The "Generate Bill" button is disabled until items are added

### Error Messages

- **"Please enter a valid rate"**: Check that rates are positive numbers
- **"Please fill all fields with valid values"**: Complete all required fields in the add item dialog
- **"No items added yet"**: Add items before trying to generate a bill

## Data Storage

- All item data is stored locally on your device
- No internet connection required
- Data persists until you start a new bill
- Each new bill clears the previous data

## Technical Notes

- **Wastage Rate**: Fixed at 10% as per industry standard
- **Precision**: Calculations are performed with high precision
- **Rounding**: Final amounts are displayed with 2 decimal places
- **Currency**: All amounts are in Indian Rupees (₹)

## Support

For technical issues or feature requests, please contact the development team.

---

*This application is designed specifically for jewellery shop billing and follows industry-standard calculation methods.*