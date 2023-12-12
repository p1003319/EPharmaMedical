package com.example.epharma.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.epharma.R
import com.example.epharma.models.ItemModel
import com.example.epharma.ItemScreen

@Composable
fun Home(context: Context){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 25.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(android.graphics.Color.parseColor("#316431"))
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.Start)
        )

        val medicines = listOf(
            Pair(painterResource(id = R.drawable.hair_care), "Hair Care"),
            Pair(painterResource(id = R.drawable.sexual_awareness), "Sexual Care"),
            Pair(painterResource(id = R.drawable.skin_care), "Skin Care"),
            Pair(painterResource(id = R.drawable.women_care), "Women Care"),
            Pair(painterResource(id = R.drawable.baby_care), "Baby Care"),
            Pair(painterResource(id = R.drawable.elderly_care), "Elderly Care"))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
        ) {
            items(medicines) { (painter, name) ->
                MedicineCategory(imagePainter = painter, name = name)
            }
        }

        BigFeaturedCard()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(start = 16.dp, end = 16.dp), // Adjusted padding
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Personal Care",
                style = MaterialTheme.typography.subtitle1.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(android.graphics.Color.parseColor("#316431"))
                ),
                modifier = Modifier
                    .width(150.dp)
                    .align(Alignment.CenterVertically)
            )

        }

        // Featured Medicines Row
        val personal_care = listOf(
            ItemModel("Himalaya Gentle Baby Wipes",
                "https://firebasestorage.googleapis.com/v0/b/epharmav1.appspot.com/o/product%2Fdiper.jpg?alt=media&token=59a338a3-ffae-4b0d-9b22-dd5225f6f48b","Information about Himalaya Gentle Baby Wipes (72 Each) Super Saver Pack\n" +
                        "Himalaya Gentle Baby Wipes are alcohol-free wipes that gently cleanse the baby’s skin. The wipes are free from silicones and lanolin and are infused with the goodness of aloe vera and Indian lotus. The wipes are mild enough to soothe the baby’s bottom during a diaper change. They are ideal for use when travelling and maintaining the hygiene of the baby on the go.\n" +
                        "\n" +
                        "Key Ingredients:\n" +
                        "Aqua, Glycerin, Aloe Barbadensis Leaf Juice, Chlorphenesin, Caprylyl Glycol, PEG-40 Hydrogenated Castor Oil, Disodium Cocoamphodiacetate, Fragrance, Nelumbo Nucifera Flower Extract, Sodium Benzoate, Lactic Acid, Aloevera, lotus",308),

            ItemModel("Parachute 100% Pure Coconut Oil",
                "https://firebasestorage.googleapis.com/v0/b/epharmav1.appspot.com/o/product%2Fparachut.jpg?alt=media&token=d4469729-6f51-43dc-9a6f-21bb73ff9f89","Information about Parachute 100% Pure Coconut Oil\n" +
                        "Parachute 100% Pure Coconut Oil is made from naturally sun-dried coconuts. It helps to provide hydration and nourishment to the scalp and body. It goes through a 5 stage purification process and 27 quality tests.\n" +
                        "\n" +
                        "Key Ingredients:\n" +
                        "Coconut oil\n" +
                        "\n" +
                        "Key Benefits:\n" +
                        "Made from 100% pure coconut oil\n" +
                        "Sourced from sun-dried coconuts, it is a healthy addition to one’s diet\n" +
                        "It has a natural aroma without any added scent\n" +
                        "Graded as edible oil, it is free from preservatives and chemicals\n" +
                        "Unique tamper-proof seal- the seal of trust\n",254),


            ItemModel("Nivea Nourishing Lotion ",
               "https://firebasestorage.googleapis.com/v0/b/epharmav1.appspot.com/o/product%2Fbody_lotion.jpg?alt=media&token=f13a6a96-e319-470d-9e2a-ccb828ab41c2","Information about Nivea 5 in 1 Complete Care Nourishing Lotion | Smooth Milk Body Lotion with Shea Butter\n" +
                        "Nivea Smooth Milk Body Lotion with Shea Butter is enriched with shea butter and deep moisture serum to moisturise dry skin and give it a silky smooth feeling that can last up to 48 hours.\n" +
                        "\n" +
                        "Key Ingredients:\n" +
                        "Aqua, Glycerin, Cetearyl Alcohol, Isohexadecane, Isopropyl Palmitate, Paraffinum Liquidum, Glyceryl Stearate SE, Butyrospermum Parkii Butter, Dimethicone, Glyceryl Stearate Citrate, C15-19 Alkane, Butylphenyl Methylpropional, Benzyl Alcohol, Citronellol, Alpha-Isomethyl Ionone, BHT, Perfume\n" +
                        "\n" +
                        "\n" +
                        "Key Benefits:\n" +
                        "The body lotion provides up to 48 hours of moisturisation\n" +
                        "The deep moisture serum makes the skin soft and smooth\n" +
                        "It hydrates the skin and helps it retain moisture",400),

            ItemModel("Veet Hair Removal Cream for Women",
                "https://firebasestorage.googleapis.com/v0/b/epharmav1.appspot.com/o/product%2Fveet.jpg?alt=media&token=ac55273f-b674-407e-a3e5-99ce2356ffaf","Information about Veet Pure Hair Removal Cream for Women | No Ammonia Smell | For Normal Skin\n" +
                        "Veet Pure Hair Removal Cream for Women with No Ammonia Smell Normal Skin\n" +
                        "It is an efficient hair removal formula for normal skin types. It is a safe style to remove hair that takes only a few minutes. It has shea butter which leaves the skin moisturised for a long time.\n" +
                        "\n" +
                        "Key Ingredients:\n" +
                        "Potassium Thioglycolate, Paraffinum Liquidum, Cetearyl Alcohol, Calcium Hydroxide, Glycerin, Shea Butter, Sorbitol, Magnesium Trisilicate, Potassium Hydroxide, Propylene Glycol, Lithium Magnesium Sodium Silicate\n" +
                        "\n" +
                        "Key Benefits:\n" +
                        "It is easy to use and removes even short hair completely\n" +
                        "It is suitable for arms, legs, underarms and bikini lines\n" +
                        "It contains shea butter that leaves the skin smooth and moisturised\n" +
                        "It helps break down the keratin structure of the hair follicle in minutes\n" +
                        "Due to innovative formula and safe ingredients, it lasts longer than shaving\n" +
                        "It also minimises future hair regrowth",86),

            ItemModel("Pedigree for Dogs",
                "https://firebasestorage.googleapis.com/v0/b/epharmav1.appspot.com/o/product%2Fpedigree.jpg?alt=media&token=5a3ecc96-78f9-4427-a771-de39c580ba16","Information about Pedigree Biscrok Protein & Calcium Biscuits for Dogs with Milk & Chicken\n" +
                        "Pedigree Biscrok Biscuits for Dogs with Milk & Chicken are crunchy and nutritious bone-shaped biscuits perfect for daily training sessions or simply as a small snack in between meals. It can provide your dog nutrition with the tasty flavour of milk and chicken. These biscuits are also a good source of protein, calcium, vitamins and minerals.\n" +
                        "Key Ingredients:\n" +
                        "Skimmed Milk\n" +
                        "Chicken and Chicken By-Products\n" +
                        "Iodised Salt\n" +
                        "Soybean Oil\n" +
                        "Rice\n" +
                        "Vitamins and Minerals\n" +
                        "Key Benefits:\n" +
                        "Crunchy dog treats with a tasty chicken and milk flavour and important nutrients for your dog's proper growth\n" +
                        "Enriched with protein and calcium for developing strong bones and teeth\n" +
                        "Contains essential vitamins, minerals and omega fatty acids to boost the overall health of your dog\n" +
                        "An ideal snack to reward your pet with during training sessions",300),
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 16.dp, end = 16.dp), // Adjusted padding
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(personal_care) { medicine ->
                FeaturedMedicineCard(medicine = medicine,context)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(start = 16.dp, end = 16.dp), // Adjusted padding
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Headache",
                style = MaterialTheme.typography.subtitle1.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(android.graphics.Color.parseColor("#316431"))
                ),
                modifier = Modifier
                    .width(90.dp)
                    .align(Alignment.CenterVertically)
            )

        }

        // Featured Medicines Row
        val featuredMedicines2 = listOf(
            FeaturedMedicine2(painterResource(id = R.drawable.burger), "Medicine 1",  "$10"),
            FeaturedMedicine2(painterResource(id = R.drawable.burger), "Medicine 2",  "$25"),
        )

        val winter_care = listOf(
            ItemModel("Volini Pain Relief Gel",
                "https://firebasestorage.googleapis.com/v0/b/epharmav1.appspot.com/o/product%2Fvolini.jpg?alt=media&token=61d3b2ac-dbf1-4e96-997b-19f24c90b611","Information about Volini Pain Relief Gel for Muscle, Joint & Knee Pain\n" +
                        "Volini Pain Relief Gel\n" +
                        "Get quick relief from body pain with the Volini spray. The spray can offer quick relief from pain in the muscles and joints. It can work wonders on sprains. Volini may work on the muscles and bones by effectively penetrating and acting upon the problematic area. It is a scientifically produced formula that might work well on the knee, joint, shoulders, back, and neck pains. The tiny particles of the spray infiltrate the skin and stop the pain in the affected area. Volini is highly effective in the case of sports injuries as well, be it muscle pulls, slight strains, or even sprains.\n" +
                        "\n" +
                        "Key Ingredients:\n" +
                        "Diclofenac Diethylamine Equivalent to Diclofenac Sodium 1% w/w\n" +
                        "Methyl Salicylate IP\n" +
                        "Menthol IP\n" +
                        "Virgin Linseed Oil BP\n" +
                        "Preservative\n" +
                        "Benzyl Alcohol IP\n" +
                        "Gel Base",208),

            ItemModel("Friends Premium Adult Dry Pants",
                "https://firebasestorage.googleapis.com/v0/b/epharmav1.appspot.com/o/product%2Fpants.jpg?alt=media&token=4f66520e-289b-445f-94a4-b0cfc017d0c4","Information about Friends Premium Adult Dry Pants Medium\n" +
                        "Friends Premium Diaper Pants comes with brief-like leg openings that make it easy to wear. These Adult Diaper Pants M-L Size are specially designed for Indian body type which provides a snug fit: Waist Size (25.6\"- 39.4\" Inch) and (65 -100 cm). Friends Premium Adult Diapers Pants provide up to 8 hours of protection due to their super-absorbent core with a rapid absorption layer. Friends Adult Diaper Pants ensure worry-free odour control. These Adult Diaper Pants have an anti-bacterial SAP to reduce rashes and infections. Moreover, it comes in three different sizes namely, M-L, XL-XXL, and L-XL.\n" +
                        "\n" +
                        "Key Ingredients: \n" +
                        "Aloe vera\n",250),


            ItemModel("Breathe Free Vaporizer",
                "https://firebasestorage.googleapis.com/v0/b/epharmav1.appspot.com/o/product%2Finhaler.jpg?alt=media&token=94d8a2af-9093-4e7e-b4a2-b390678fc430","Information about Dr Morepen VP 06 Breathe Free Vaporizer\n" +
                        "Dr Morepen VP-06 Breathe Free Vaporizer is a mechanical device that turns water into steam and transmits that steam into the surrounding atmosphere. It is used to help clear congestion or moisturize dry nasal passageways and provide relief from various respiratory and throat-related problems like asthma, bronchitis, laryngitis, common cold etc.\n" +
                        "\n" +
                        "Uses:\n" +
                        "This device is used to clear the nasal passage and alleviate various respIratory and throat-related problems like the common cold, bronchitis, sinusitis, hay fever, asthma, laryngitis, throat irritation etc.\n" +
                        "It can also be used for treating stuffy head and nose conditions\n" +
                        "Product Specifications and Features:\n" +
                        "It is a steam inhalator and facial sauna that produces steam vapours in seconds\n" +
                        "It is highly effective and easy to use and clean\n" +
                        "It has a plastic body to prevent shocks\n" +
                        "It is portable, ideal for home use, and can be conveniently carried during travel as well",320),

            ItemModel("Dabur Chyawanprash",
                "https://firebasestorage.googleapis.com/v0/b/epharmav1.appspot.com/o/product%2Fchawanprash.jpg?alt=media&token=df47fb9c-2e7f-45fa-ae26-510177df5ddc","Overall Health\n" +
                        "Dabur Chyawanprash Awaleha is based on an ancient Ayurvedic formulation of more than 40 herbs and other ingredients to boost your immunity. Its herbal composition naturally strengthens the body's immune system to fight against common infections like colds, coughs, etc.\n" +
                        "\n" +
                        "Key Ingredients:\n" +
                        "Amla, Ashwagandha, Hareetaki, Dashmul, Ghrit, Pippali, Shatavari, Giloy, Mulethi, Bilva, Shati, Gokshura, Utpala, Haritaki\n" +
                        "\n" +
                        "Indications:\n" +
                        "It is indicated in cases of poor immunity and weak immune system\n" +
                        "\n" +
                        "Key Benefits:\n" +
                        "It is carefully formulated using 40 powerful herbal ingredients\n" +
                        "It boosts your immunity to keep away common and seasonal infections\n" +
                        "It is also beneficial for healthy digestion\n" +
                        "It is suitable for your energy and overall well-being\n" +
                        "Power-packed with antioxidant benefits and has a sweet and sour flavour",700),

                ItemModel("Electrolyte Drink",
                    "https://firebasestorage.googleapis.com/v0/b/epharmav1.appspot.com/o/product%2Fchawanprash.jpg?alt=media&token=df47fb9c-2e7f-45fa-ae26-510177df5ddc","Information about Enerzal Energy & Electrolyte Drink | Flavour Powder Orange\n" +
                            "Enerzal Energy Drink Powder is an energy drink which helps to replenish vital electrolytes which are lost due to sweat. It can be taken when feeling exhausted, fatigued, and low in energy. It helps provide instant and sustained energy and maintains the electrolyte balance.\n" +
                            "\n" +
                            "Key Ingredients:\n" +
                            "Sucrose, Glucose, Acidity Regulator, Sodium Citrate, Sodium Chloride, Potassium Chloride, Sodium Acid Phosphate, Magnesium Sulphate, Calcium Lactate\n" +
                            "\n" +
                            "Key Benefits:\n" +
                            "Glucose can help provide instant energy\n" +
                            "Sucrose helps provide sustained energy\n" +
                            "Sodium aids in maintaining the electrolytes balance in the body\n" +
                            "Potassium helps prevent muscle cramps\n" +
                            "Magnesium assists in maintaining muscle and nerve functions\n" +
                            "Calcium helps build bone strength\n" +
                            "Chloride maintains water balance in the body",50),
            )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 16.dp, end = 16.dp), // Adjusted padding
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(winter_care) { medicine2 ->
                FeaturedMedicineCard(medicine = medicine2,context = context)
            }
        }
    }
}



@Composable
fun FeaturedMedicineCard2(medicine2: FeaturedMedicine2) {
    Card(
        modifier = Modifier
            .width(155.dp)
            .height(160.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(android.graphics.Color.parseColor("#D3F8D3")))
                .clickable { /* Handle card click */ },
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = medicine2.imagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(start = 18.dp, end = 18.dp, top = 18.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    )
            )
            Spacer(modifier = Modifier.height(5.dp))

            // Medicine Name and Price in the same line
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = medicine2.medicineN,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .width(70.dp)
                        .align(Alignment.CenterVertically)
                )

                Text(
                    text = medicine2.priceN,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        textAlign = TextAlign.Left
                    ),
                    modifier = Modifier.width(30.dp)
                )
            }
        }
    }
}
data class FeaturedMedicine(
    val imagePainter: Painter,
    val medicineName: String,
    val price: String
)




@Composable
fun FeaturedMedicineCard(medicine: ItemModel,context: Context) {
    Card(
        modifier = Modifier
            .width(155.dp)
            .height(160.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp
    ) {
        val localContext = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(android.graphics.Color.parseColor("#D3F8D3")))
                .clickable {
                    val intent = Intent(context, ItemScreen::class.java)
                    Log.e("manan1",medicine.price.toString()+"")
                    intent.putExtra("name",medicine.name)
                    intent.putExtra("cost",medicine.price.toString())
                    intent.putExtra("description",medicine.description)
                    intent.putExtra("image_url",medicine.image)
                    localContext.startActivity(intent)
                },
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = rememberImagePainter(medicine.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .padding(start = 18.dp, end = 18.dp, top = 18.dp)
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 8.dp
                        )
                    )
            )
            Spacer(modifier = Modifier.height(5.dp))

            // Medicine Name and Price in the same line
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = medicine.name,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .width(70.dp)
                        .align(Alignment.CenterVertically)
                )

                Text(
                    text = medicine.price.toString(),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        textAlign = TextAlign.Left
                    ),
                    modifier = Modifier.width(30.dp)
                )
            }
        }
    }
}


@Composable
fun BigFeaturedCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(16.dp)),
        elevation = 4.dp
    ) {
        Image(
            painter = painterResource(id = R.drawable.card),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        )
    }
}




@Composable
fun MedicineCategory(imagePainter: Painter, name: String) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = name,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 2.dp),
        )
    }
}

data class FeaturedMedicine2(
    val imagePainter: Painter,
    val medicineN: String,
    val priceN: String
)