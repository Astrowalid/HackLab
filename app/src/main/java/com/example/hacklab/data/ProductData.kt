package com.example.hacklab.data

import androidx.compose.ui.graphics.Color
import com.example.hacklab.R

data class Product(
    val id: Int,
    val name: String,
    val author: String,
    val price: Double,
    val category: String,
    val imageResId: Int,
    val description: String = "",
    val specifications: Map<String, String> = emptyMap(),
    val safetyDisclaimer: String = ""
)

object ProductRepository {
    val sampleProducts = listOf(
        Product(
            id = 1,
            name = "Stealth Recon Toolkit",
            author = "By @CyberNinja",
            price = 49.99,
            category = "Recon",
            imageResId = R.drawable.hacklab_white,
            description = "A compact, Raspberry Pi-based tool for covert network reconnaissance and penetration testing. Includes pre-configured scripts for passive and active scanning, credential harvesting, and exploit execution.",
            specifications = mapOf(
                "Platform" to "Raspberry Pi 4",
                "Storage" to "64GB MicroSD",
                "Connectivity" to "WiFi 802.11ac",
                "Power" to "USB-C",
                "Bluetooth" to "5.0"
            ),
            safetyDisclaimer = "This tool is intended for ethical hacking and security testing purposes only. Use responsibly and within legal boundaries. The seller is not responsible for misuse or damage caused by this product."
        ),
        Product(
            id = 2,
            name = "Advanced Exploit Suite",
            author = "By @ShadowHacker",
            price = 79.99,
            category = "Exploits",
            imageResId = R.drawable.hacklab_white,
            description = "Professional-grade exploitation framework with advanced payload generation and delivery mechanisms. Designed for authorized security assessments and penetration testing.",
            specifications = mapOf(
                "Framework" to "Metasploit-based",
                "Payloads" to "500+ variants",
                "OS Support" to "Linux, Windows, macOS",
                "Encoding" to "AES-256",
                "Updates" to "Lifetime"
            ),
            safetyDisclaimer = "This tool is intended for ethical hacking and security testing purposes only. Use responsibly and within legal boundaries. The seller is not responsible for misuse or damage caused by this product."
        ),
        Product(
            id = 3,
            name = "Post-Exploitation Framework",
            author = "By @GhostSec",
            price = 59.99,
            category = "Post-Exploitation",
            imageResId = R.drawable.hacklab,
            description = "Comprehensive post-exploitation toolkit for maintaining access, privilege escalation, and data exfiltration in authorized environments.",
            specifications = mapOf(
                "Languages" to "Python, Bash, PowerShell",
                "C2 Support" to "Multi-Protocol",
                "Stealth" to "Anti-forensics included",
                "Modules" to "200+",
                "Compatibility" to "Windows Server 2016+"
            ),
            safetyDisclaimer = "This tool is intended for ethical hacking and security testing purposes only. Use responsibly and within legal boundaries. The seller is not responsible for misuse or damage caused by this product."
        ),
        Product(
            id = 4,
            name = "Network Mapper Pro",
            author = "By @AnonOps",
            price = 39.99,
            category = "Recon",
            imageResId = R.drawable.hacklab,
            description = "Advanced network reconnaissance and mapping tool with passive and active scanning capabilities. Includes vulnerability detection and asset inventory.",
            specifications = mapOf(
                "Scan Types" to "SYN, UDP, ICMP",
                "Range" to "Class A-C networks",
                "Export" to "JSON, CSV, XML",
                "Filtering" to "Advanced regex",
                "Performance" to "10K hosts/min"
            ),
            safetyDisclaimer = "This tool is intended for ethical hacking and security testing purposes only. Use responsibly and within legal boundaries. The seller is not responsible for misuse or damage caused by this product."
        ),
        Product(
            id = 5,
            name = "Wireless Penetration Kit",
            author = "By @NetHunter",
            price = 69.99,
            category = "Exploits",
            imageResId = R.drawable.hacklab_white,
            description = "Specialized toolkit for wireless network security testing. Includes encryption cracking, rogue AP deployment, and client isolation attacks.",
            specifications = mapOf(
                "Protocols" to "802.11 a/b/g/n/ac/ax",
                "Encryption" to "WEP, WPA, WPA2, WPA3",
                "Range" to "100+ meters",
                "Antenna" to "Dual-band directive",
                "Power" to "30dBm max"
            ),
            safetyDisclaimer = "This tool is intended for ethical hacking and security testing purposes only. Use responsibly and within legal boundaries. The seller is not responsible for misuse or damage caused by this product."
        ),
    )


    fun getProductById(id: Int): Product? {
        return sampleProducts.find { it.id == id }
    }
}