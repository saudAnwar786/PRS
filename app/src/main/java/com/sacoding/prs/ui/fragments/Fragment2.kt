package com.sacoding.prs.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.sacoding.prs.Adapter.SupportAdapter
import com.sacoding.prs.R
import com.sacoding.prs.data.models.SupportItem
import com.sacoding.prs.databinding.FragmentSupportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Fragment2 :Fragment(R.layout.fragment_support){
    private lateinit var binding: FragmentSupportBinding
    private lateinit var supportAdapter : SupportAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentSupportBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val items= listOf<SupportItem>(
            SupportItem(
                R.drawable.datapreprocessing,
                "Data Preprocessing",
                "The code filters out articles and customers with low frequencies of interactions and is stored in a frequency DataFrame."
            ),
            SupportItem(
                R.drawable.graph,
                "Creating a Graph",
                "The interactions between customers and articles are merged into the GraphTravel_HM DataFrame"
            ),
            SupportItem(
                R.drawable.generatingwalks,
                "Generating Walks",
                "We implement a biased random walk on the graph, which explores the graph " +
                        "and determines the next node. We generate random walks for a specified " +
                        "number of times and each random walk has a certain length."
            ),
            SupportItem(
                R.drawable.wordtovec,
                "Word2Vec Training",
                "The training data corresponds to the sequences of nodes generated from random" +
                        " walks on the graph of customer-item interactions. Each sequence of nodes represents " +
                        "a walk that a \"walker\" takes in the graph, simulating how a user interacts with different items.\n" +
                        "The sequences of nodes from the walks are used to train the Word2Vec model, which " +
                        "learns embeddings that capture the relationships between users and items.\n"
            ),
            SupportItem(
                R.drawable.ecommerce,
                "E-commerce Platforms",
                "A user browsing an e-commerce platform can receive personalized product recommendations based on their past purchases, " +
                        "and the semantic similarity of products. The system can suggest complementary items that users might be interested in purchasing " +
                        "together, enhancing cross-selling opportunities"
            ),
            SupportItem(
                R.drawable.streamingservices,
                "Streaming Services",
                "A Word2Vec-powered recommendation system can suggest movies, TV shows, or songs that share similar themes, genres, or content " +
                        "attributes, enhancing the user's entertainment experience."
            )

        )
        supportAdapter= SupportAdapter(items)
        binding.supportViewPager.apply {
            adapter=supportAdapter
        }
    }
}