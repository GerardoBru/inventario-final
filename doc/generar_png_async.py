#!/usr/bin/env python3
import asyncio
from playwright.async_api import async_playwright
from pathlib import Path

async def screenshot_to_png():
    doc_path = Path(r"c:\Users\chancro\Desktop\Inventario Final\doc")
    
    async with async_playwright() as p:
        browser = await p.chromium.launch()
        
        # Diagrama de Clases
        print("Generando Diagrama de Clases...")
        page = await browser.new_page(viewport={'width': 1920, 'height': 1200})
        html_file = doc_path / "diagrama_clases_simple.html"
        await page.goto(f"file:///{html_file.as_posix()}")
        await page.wait_for_timeout(3000)
        await page.screenshot(path=str(doc_path / "DIAGRAMA_CLASES_SIMPLE.png"), full_page=True)
        await page.close()
        print("✓ DIAGRAMA_CLASES_SIMPLE.png creado")
        
        # Diagrama de Casos de Uso
        print("Generando Diagrama de Casos de Uso...")
        page = await browser.new_page(viewport={'width': 1920, 'height': 1200})
        html_file = doc_path / "diagrama_casos_uso_simple.html"
        await page.goto(f"file:///{html_file.as_posix()}")
        await page.wait_for_timeout(3000)
        await page.screenshot(path=str(doc_path / "DIAGRAMA_CASOS_USO_SIMPLE.png"), full_page=True)
        await page.close()
        print("✓ DIAGRAMA_CASOS_USO_SIMPLE.png creado")
        
        await browser.close()
        print(f"\n✓ Archivos guardados en: {doc_path}")

if __name__ == "__main__":
    asyncio.run(screenshot_to_png())
