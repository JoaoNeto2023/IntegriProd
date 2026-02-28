import type { Config } from 'tailwindcss'

const config: Config = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      colors: {
        navy: {
          950: '#050d1a',
          900: '#0a1628',
          800: '#0f2044',
          700: '#162b5c',
          600: '#1d3a7a',
          500: '#2350a0',
          400: '#3168c8',
          300: '#5b8de8',
          200: '#93b8f5',
          100: '#c8dcfb',
          50: '#eaf2ff',
        },
        accent: {
          DEFAULT: '#00d4aa',
          dark: '#00a882',
        },
        surface: {
          DEFAULT: '#0c1d3a',
          2: '#102240',
          3: '#162b52',
        },
      },
      fontFamily: {
        sans: ['DM Sans', 'sans-serif'],
        display: ['Syne', 'sans-serif'],
      },
    },
  },
  plugins: [],
}

export default config